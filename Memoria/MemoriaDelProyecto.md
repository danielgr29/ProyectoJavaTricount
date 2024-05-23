Archivo donde se registra toda la memoria del proyecto

Para la base de datos: la pk de cada cliente o usuario es el [nombre]

Cambiar la lista de miembros: list<nombreCliente> que se encuentra en la clase Grupos

En grupos:
  añadir un [Id] de Grupo como pk, que se encuentra en la clase Grupos 

Para el cliente:
  cambiar agregarGrupo, por salir del grupo y quitar la opcion de eliminar gastos. 
  

03-05-2024
	
	Comentamos entre los tres el diagrama de clases e hicimos una primera version.
	Decidimos los campos y las tablas de la base de datos
	Nos dividimos parte del trabajo para ir empezando

		Rafa: Metodos del usuario
			-String iniciarSesion()	//Pide usuario y contraseña y devuelve el usuario si los datos son correctos
			
			-void crearUsuario()	//Pide usuario y contraseña y comprueba que sean validos antes de crearlo
			
			-void usuarioValido(String usuario)	//Comprueba si el nombre de usuario cumple los requisitos
			
			-boolean usuarioExiste(String usuario)	//Comprueba si el nombre de usuario esta en la base de datos
			
			-void contraseniaValida(String pass)	//Comprueba si la contraseña cumple los requisitos
			
			-boolean comprobarContraseniaCorrecta(String usuario, String pass)	//Comprueba en la base de datos si los datos de usuario y contraseña existen
		
		Dani: Metodos para crear, borrar y calcular los gastos
			
			-crearGasto()
			
			-eliminarGasto()
			
			-calcularGasto()
			
		
		Alvaro: Creacion de la base de datos y los metodos para interactuar con ella desde el programa



07-05-2024
	
	Diagrama de clases terminado

	Base de datos comenzada

	Metodos anteriores comenzados



10-05-2024

	Inicio de las clases:
		ConBD

		CrearBD

		Gastos

		Grupo

		Main

		Usuario



12-05-2024
	
	Creación de clase conectadora de base de datos, arreglado CrearBD y añadido ejemplos en Main

	Se añadió más contenido del Usuario

	Se añadió más contenido del Main

	Se añadió la excepcion de valorInvalido

	Se añadio más contenido del Grupo
	
	

14-05-2024

	Terminado Diagrama de casos de uso 

	Ampliado Usuario y empezado menú. 

	Añadida excepción personalizada



18-05-2024

	typo

	modificado las tablas grupos y gastos de la base de datos para que sus claves primarias sean autoincrementables
	
	Añadido modelo relacional	
	


19-05-2024

	Se cambio parte de Usuario

	Se cambio parte del Main

	Se cambio parte de Grupo


20-05-2024

	Definido el Main del programa en general, creado sistema de gestion de grupos y añadido funcion de ver y agregar gastos

	Agregado DiagramaDeCasosDeUso.drawio.pdf
 	
  	Agregado DiagramaDeCasosDeUso2.drawio.pdf


21-05-2024

	Lista de todos los metodos de las clases



22-05-2024

 	Ultima versión del programa
	Update Gasto.java
	Update ConBD.java
	Update Grupo.java
	Update Main.java
	Update Usuario.java
	Update ValorInvalidoException.java
	Update Grupo.java - Se quitó una parte que sobraba
	Agregado DiagramasDeClase_03.drawio.drawio.pdf
