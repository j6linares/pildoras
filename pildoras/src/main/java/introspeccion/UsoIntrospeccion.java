package introspeccion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class UsoIntrospeccion {

	public static void main(String[] args) {
		Scanner entrada=new Scanner(System.in);
		System.out.println("Introduce el nombre de la calse: ");
		String nombreClase= entrada.next();
		//imprime clase y superclase
		try{
			Class clase=Class.forName(nombreClase);
			Class superClase = clase.getSuperclass();
			System.out.println("Clase="+clase.getName());
			if (superClase!=null && superClase!=Object.class){
				System.out.println(" extends "+superClase.getName());
			}
			System.out.println("\nconstructores:");
			imprimirContructores(clase);
			System.out.println("\nmétodos:");
			imprimeMetodos(clase);
			System.out.println("\natributos:");
			imprimeAtributos(clase);
			
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

	private static void imprimirContructores(Class clase) {
		Constructor[] constructores = clase.getDeclaredConstructors();
		for (Constructor constructor : constructores) {
			//imprime modificadores y tipo
			System.out.print(" "+Modifier.toString(constructor.getModifiers()));
			System.out.print(" "+constructor.getName()+"(");
			//imprime tipo parametros
			Class[] tipoParametros=constructor.getParameterTypes();
			for (int i = 0; i < tipoParametros.length; i++) {
				if (i>0) System.out.print(", ");
				System.out.print(tipoParametros[i].getName());
			}
			System.out.println(");");
		}
		
	}

	private static void imprimeAtributos(Class cl) {
		Field[] atributos=cl.getDeclaredFields();
		for (Field atributo : atributos) {
			Class tipoAtributo=atributo.getType();
			String nombreAtributo=atributo.getName();
			//imprime modificadores y tipo
			System.out.print(" "+Modifier.toString(atributo.getModifiers()));
			System.out.println(" "+tipoAtributo.getName()+" "+nombreAtributo+";");
		}
		
	}

	private static void imprimeMetodos(Class cl) {
		Method[] metodos =cl.getMethods();
		for (Method metodo: metodos) {
			Class tipoDevuelto=metodo.getReturnType();
			String nombreMetodo = metodo.getName();
			//imprime modificadores y tipo
			System.out.print(" "+Modifier.toString(metodo.getModifiers()));
			System.out.print(" "+tipoDevuelto.getName()+" "+nombreMetodo+"(");
			//imprime tipo parametros
			Class[] tipoParametros=metodo.getParameterTypes();
			for (int i = 0; i < tipoParametros.length; i++) {
				if (i>0) System.out.print(", ");
				System.out.print(tipoParametros[i].getName());
			}
			System.out.println(");");
		}
		
	}

}
