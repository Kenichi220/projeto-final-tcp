# projeto-final-tcp

- Kenichi Brumati ([@Kenichi220](https://github.com/Kenichi220))
- Lorenzo Cadó Nemitz ([@Lorenzonemitz](https://github.com/Lorenzonemitz))

Necessário ter JavaFX
 
----------------------------------------------------------------------------------------- 

No IntelliJ

  -> Abra o projeto
  
  -> File --> Project Structure --> Libraries
  
  -> Clica no botão +, adicione um Java, vá até o diretorio "lib" do seu JavaFX, de OK
  
-----------------------------------------------------------------------------------------

  -> Run --> Edit Configurations --> Modify Options --> Add VM Options
  
  -> Cole o seguinte: 

--module-path seu-diretorio-lib-do-JavaFX --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.media,javafx.swing,javafx.web 
