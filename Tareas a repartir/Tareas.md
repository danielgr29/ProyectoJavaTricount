Archivo donde se repartiran todas las tareas entre los miebros del grupo:

METODOS:

Metodos del usuario:
	
	VerGrupo();
	AgregarUsuario(); //, de los que debe conocer el nombre
	CrearGrupo();//Al crear el grupo se convierte en administrador del mismo
	EliminarGrupo();// Sólo si es administrador.
	EntrarEnGrupo();// para ver los gastos que tiene.
	


Metodos de Grupo:

	AñadirGasto();
	EliminarGasto();
	VerSaldo();//Calcular la diferencia entre los gastos pagados y la media de gastos que lleva el grupo


Tareas: 

Rafa(Jefe)
	Main

	

Álvaro:
Extra 1: Creación de bases de datos para guardar la información.
			Funcion para descargar la base de datos.(Con uso de variables )
			Funcion para guardar los cambios en la base de datos. (Update)
	
			




Daniel Gutiérrez:

	Entorno amigable y de fácil uso.

	presentacion 

	repositorio

	Metodos: Añadir un gasto. // Eliminar un gasto. // Clases grupo, Usuarios, gastos// VerSaldo();





Existir un menú para Registrarse como usuario o autentificarse

Los usuarios deben autentificarse con nombre de usuario y contraseña


Una vez autentificado, un usuario puede:

		Ver los grupos a los que pertenece.

		Crear uno grupo nuevo, en los que puede agregar usuarios ya existentes, de los que
		debe conocer el nombre. Al crear el grupo se convierte en administrador del mismo.

		Eliminar un grupo. Sólo si es administrador.

		Entrar en un grupo para ver los gastos que tiene.


Al entrar en el grupo el usuario podrá:
		
		Añadir un gasto.

		Eliminar un gasto.

		Ver el saldo (Calcular la diferencia entre los gastos pagados y la media de gastos que
		lleva el grupo)

		Ejemplo:
		Pepe ________ -4 €
		Ana _________ -5 €
		Luisa ________ +9 €

		MostrarListaGastos, ordenada por fecha.


Cada gasto es pagado por un usuario, y será común para todos los participantes del grupo.

		Elaboración de diagrama de clases y casos de uso.
		
		Entorno amigable y de fácil uso.

		Extra 1: Creación de bases de datos para guardar la información.
		
		Extra 2: Por otro lado debe establecer una forma de cerrar los gastos de forma que se pueda
		dar dinero de una persona a otra para cerrar el viaje con el saldo a cero todos los
		componentes.

