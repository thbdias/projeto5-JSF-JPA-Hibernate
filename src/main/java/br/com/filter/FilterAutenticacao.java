package br.com.filter;

import java.io.IOException;
import java.net.HttpRetryException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.jpa.util.JpaUtil;

@WebFilter (urlPatterns = "/*") //interceptar todas páginas
public class FilterAutenticacao implements Filter {

	//executado em todas requisicoes
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("****** Invocando filter ********");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		//retorna null caso não esteja logado
		String userLogado = (String) session.getAttribute("usuarioLogado");
		
		//url que estou tentando acessar
		String urlParaAutenticar  = req.getServletPath();
		
		//usuario não logado
		if (userLogado == null && !urlParaAutenticar.equalsIgnoreCase("index.jsf")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			
			return; // para o processo para redirecionar
		}
		
		
		//executa as acoes do request e do response
		chain.doFilter(request, response);
	}
	
	//executado quando está subindo servidor
	@Override
	public void init(FilterConfig config) throws ServletException{
		//levantar conexão com BD
		System.out.println(" ********* Levantando BD *********");
		JpaUtil.getEntityManger();
	}

	
	
}
