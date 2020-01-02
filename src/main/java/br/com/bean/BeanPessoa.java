package br.com.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

//classe responsável por tratar uma página jsf
@ViewScoped
@ManagedBean(name = "beanPessoa")
public class BeanPessoa {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();

	// método que será utilizado pela tela jsf
	public String salvar() {
		//daoGeneric.salvar(pessoa); //apenas salva no BD e não retorna nada
		pessoa = daoGeneric.merge(pessoa); //salva ou atualiza e retorna o objeto salvo no BD
		carregarPessoas();
		mostrarMsg("Cadastrado com sucesso!");
		return "";
	}
	
	public void mostrarMsg(String msg) {
		//acessar a parte de contextos do JSF - ambiente que está rodando
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
	public String novo() {
		//executa algum processo antes de novo
		pessoa = new Pessoa();
		return "";
	}
	
	public String limpar() {
		//executa algum processo antes de limpar
		pessoa = new Pessoa();
		return "";
	}
	
	public String remove() {
		// daoGeneric.delete(pessoa); //apresenta o erro: detached
		daoGeneric.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMsg("Removido com sucesso!");
		return "";
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {	
		try {
			
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
			URLConnection connection = url.openConnection(); //abrindo conexao
			//ler fluxo de dados
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			//converte um json em string para uma determinada classe
			Pessoa pessoaGsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setCep(pessoaGsonAux.getCep());
			pessoa.setLogradouro(pessoaGsonAux.getLogradouro());
			pessoa.setComplemento(pessoaGsonAux.getComplemento());
			pessoa.setBairro(pessoaGsonAux.getBairro());
			pessoa.setLocalidade(pessoaGsonAux.getLocalidade());
			pessoa.setUf(pessoaGsonAux.getUf());
			pessoa.setUnidade(pessoaGsonAux.getUnidade());
			pessoa.setIbge(pessoaGsonAux.getIbge());
			pessoa.setGia(pessoaGsonAux.getGia());
			
			System.out.println("\n\n\njson: " + jsonCep + "\n\n");
			
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("Erro ao consultar o cep");
		}
	}
	
	//sempre que se abrir a tela jsf desse managedBean e ele for instanciado, criado em memória, ele vai carregar o método que está anotado com @PostConstruct
	@PostConstruct 
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public String logar() {		
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if (pessoaUser != null) {//achou usuario
			//add usuario na session
			//pq vai cair no filter de autenticacao e irá fazer a validação
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);
			
			return "primeirapagina.jsf";
		}		
		return "index.jsf";
	}
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoaUser.getPerfilUser().equals(acesso);
	}

}








