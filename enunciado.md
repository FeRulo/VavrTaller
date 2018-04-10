Etapa I: Drones para domicilios, primera parte.

La empresa “Su corrientazo a domicilio” ha contratado a S4N para el desarrollo de una aplicación capaz de operar un (1)
dron para que haga entrega de almuerzos a diferentes direcciones en la ciudad de Bogotá.

Para la primera entrega del proyecto, “Su corrientazo domicilio”, ha decidido que sólo entregará domicilios a 10 cuadras
 a la redonda de su barrio, el cual puede ser representado con un plano cartesiano.
De esta manera, la posición del dron está representada por la combinación de coordenadas X y Y y una letra que
corresponde a uno de los puntos cardinales. Por simplificación, el barrio puede ser visto como una grilla. Por ejemplo,
 (0,0,N) corresponde a la posición del dron en la esquina inferior izquierda de la grilla estando el dron apuntando en
 dirección norte.

El dueño de “Su corrientazo a domicilio” será el responsable de escribir los movimientos del dron para llegar a cada
destino de cada uno de los almuerzos en el sistema. Su deseo es poder usar un archivo de texto donde anotará todos los
pedidos del día y así programar las entregas que el dron deberá realizar en el transcurso del día.
Es importante aclarar que el dron es sólo capaz de cargar hasta tres almuerzos por vez. Por esta razón, y aprovechando
el profundo conocimiento del barrio, el dueño de “Su corrientazo a domicilio” será quien defina las rutas de entrega en
el archivo de texto.

Un ejemplo del archivo de texto que ingresaría al sistema para las entregas de un día sería:
AAAAIAAD
DDAIAD
AAIADAD

Donde:
● La letra A corresponde a un movimiento hacia adelante.
● La letra I corresponde a un giro de 90 grados del dron a la izquierda.
● La letra D corresponde a un giro de 90 grados del dron a la derecha.
Aclaraciones:


Cada línea del archivo de texto corresponde a una entrega de un almuerzo.
El dron siempre inicia en la posición (0, 0) orientación Norte
Para el dueño de “Su corrientazo a domicilio” es importante monitorear el estado
del dron siempre, razón por la cual espera que el sistema le entrege información en
tiempo real de cada una de las entregas realizadas. Por esta razón solicita que una
vez terminada la ejecución de las rutas se le entregue un informe en otro archivo
de texto de la posición del dron en el plano cartesiano de cada entrega.
De esta manera, para la primera línea del ejemplo del archivo de texto anterior, se
espera que el informe se presente así:
== Reporte de entregas ==
(-2, 4) dirección Norte
(-3, 3) dirección Sur
(-4, 2) dirección Oriente
El archivo de entrada debe llamarse in.txt y el de salida out.txt. Sólo se debe leer
un archivo in.txt por vez y sólo se debe generar un archivo out.txt por ejecución
del programa.
