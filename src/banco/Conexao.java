/*
	Douglas Silva Lacerda
*/

package banco;

public class Conexao {

	private String driver	="com.mySql.jdbc.Driver";
	private String login	="root";
	private String senha	="123456";
	private String url		="jdbc:mysql://localhost/almoxarifado";
	
	/**getters e setters */
	
    public String getDriver() {return driver;}

    public void setDriver(String d) {driver = d;}

    public String getUrl() {return url;}

    public void setUrl(String u) {url = u;}

    public String getLogin() {return login;}

    public void setLogin(String l) {login = l;}

    public String getSenhaBanco() {return senha;}

    public void setSenhaBanco (String s) {senha = s;}
}