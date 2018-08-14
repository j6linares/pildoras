package jdbc.app;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import jdbc.controlador.ConexionDB;
import jdbc.vista.PaginacionRS;

public class Paginador {

	public static void main(String[] args) {
		System.out.println("Paginador");
		Scanner input = new Scanner(System.in);
		
		Connection conn;
		try {
			conn=(new ConexionDB()).conectaDB("jdbc:mysql://madppvlkin:3306/mysqldesa", "mysqldesa", "mysqld3s4");
			System.out.println("conectado!");
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String catalog = null;
			String schemaPattern = null;
			String tableNamePattern = "%";
			String[] types = null;
			ResultSet rsT = dbMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			PaginacionRS pagina=new PaginacionRS(rsT);
			
			System.out.println("Nro de l�neas por p�gina: ");
			pagina.setNroLinxPag(input.nextInt());
			System.out.println("Nro de p�ginas por bloque: ");
			pagina.setNroPagxBloq(input.nextInt());
			pagina.calcularPaginacion();
			pagina.mostrarPagina(1);
			
			String fin = "";
			do {
				System.out.println(""
						+"\n\t[[ primer bloque [ bloque anterior ] siguiente bloque ]] �ltimo bloque"
						+"\n\t(( primera p�gina del bloque ( p�gina anterior del bloque ) siguiente p�gina del bloque )) �ltima p�gina del bloque"
						+ "\n\t<< primera p�gina < pagina anterior > siguiente p�gina >> �ltima p�gina"
						+ "\n\tNro.P�gina"
						+"\n\n[[ [ (( ( << < Nro.Pag. > >> ) )) ] ]] fin=salir");
				String choice = input.next();

			    switch (choice) {
	        		case "[[":
	        			pagina.mostrarPrimeraPaginaBloque(pagina.primerBloque());
	        			break;
	        		case "[":
	        			pagina.mostrarPrimeraPaginaBloque(pagina.bloqueAnterior());
	        			break;			        
		        	case "((":
		        		pagina.mostrarPaginaBloque(pagina.primeraPaginaBloque());
		        		break;
		        	case "(":
		        		pagina.mostrarPaginaBloque(pagina.paginaAnteriorBloque());
		        		break;			        
				    case "<<":
			            pagina.mostrarPagina(pagina.primeraPagina());
			            break;
			        case "<":
			        	pagina.mostrarPagina(pagina.paginaAnterior());
			            break;
			        case ">":
			        	pagina.mostrarPagina(pagina.siguientePagina());
			            break;
			        case ">>":
			        	pagina.mostrarPagina(pagina.ultimaPagina());
			            break;
		        	case ")":
		        		pagina.mostrarPaginaBloque(pagina.siguientePaginaBloque());
		        		break;
		        	case "))":
		        		pagina.mostrarPaginaBloque(pagina.ultimaPaginaBloque());
		        		break;			        
	        		case "]":
	        			pagina.mostrarPrimeraPaginaBloque(pagina.siguienteBloque());
	        			break;			        
	        		case "]]":
	        			pagina.mostrarPrimeraPaginaBloque(pagina.ultimoBloque());
	        			break;
			        default:
			            // The user input an unexpected choice.
			        	try {
			        		int pag = Integer.parseInt(choice);
			        		pagina.mostrarPagina(pag);
			        		
			        	} catch (NumberFormatException | IndexOutOfBoundsException ex){
			        		if (!choice.equals("fin"))
			        			System.out.println("Solo n�meros de p�gina v�lidos! De 1 a "+pagina.getNroPaginas());
			        	}
			        		
			    }
			    fin=choice;
			} while (!fin.equals("fin")) ;
			
		
			
		} catch (SQLException e) {
			System.out.println("Error SQL! ");
			System.out.println("SQLState="+e.getSQLState());
			System.out.println("ErrorCode="+e.getErrorCode() );
			System.out.println("Message="+e.getMessage() );
			e.printStackTrace();
		} finally {
			input.close();
		}
	}

}
