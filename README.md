Projeto de BD de Guilherme Alcoforado Rabelo

comandos que tive que usar pra rodar isso:

javac -cp ".;lib\jar\mysql-connector-j-9.3.0.jar" -d out src\dao\*.java src\model\*.java src\view\*.java src\Main.java

java -cp ".;out;lib\jar\mysql-connector-j-9.3.0.jar" Main
