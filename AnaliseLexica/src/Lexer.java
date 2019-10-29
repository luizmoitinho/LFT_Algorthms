import  java.util.ArrayList;

public class Lexer {
	ArrayList<Token> tokens =  new ArrayList<Token>();
	Token token;
	Buffer buffer;
	public Lexer(String lexema){
		this.buffer =   new Buffer(lexema);
	}
	public Token proximoToken() {
		Character c;
		int estado =0; 			// indica o estado atual - AFD - AFND
		int ultimoFinal =-1;  	// nulo, ou seja nao existe
		buffer.marcarInicio();
		//percorre toda a string
		for(int i=0;i<buffer.lexema.length();i++) {
			c =  buffer.proximo();
			switch(estado) {
				case 0:
					//Ao entrar neste case, temos o primeiro caractere a ser lido
					// q0 -> q1
					if(delimitador(c))
						estado = 29;
					else if(c=='i') {
						ultimoFinal =  1;
						estado = 1;
						buffer.marcarUltimo();
					}
					// Verifica se � uma letra
					else if(Character.isLetter(c) && c!='i')
						this.setID(estado,ultimoFinal);
					
					// Verifica se � um digito
					//Integer.parseInt(String.valueOf(digits.charAt(i)));
					else if(Character.isDigit(c)){
						ultimoFinal =  10;
						estado = 10;
						buffer.marcarUltimo();
					}
				break;
				case 1:
					if(c=='f') {
						ultimoFinal = 2;
						estado  =2;
						buffer.marcarUltimo();
						token = new Token(0,"if");

					}
					else if(Character.isLetter(c) && c!='f')
						this.setID(estado,ultimoFinal);
					
					else if(this.validarID(c)) {
						this.setID(estado,ultimoFinal);
					}
					break;
				case 2:
					if(this.validarID(c))
						this.setID(estado,ultimoFinal);
					break;
				case 3:
					if(this.validarID(c))
						this.setID(estado,ultimoFinal);
					break;
				case 4:
					if(c =='<') {
						
					}
					break;
				case 29:
					if(!delimitador(c)) {
						buffer.retrair(1);
						buffer.marcarInicio();
						estado=0;
					}	
					break;
			}
		}
		return token;
	}
	private boolean validarID(Character c) {
		if(Character.isLetter(c) || Character.isDigit(c)) {
			return true;
		}
		else
			return false;
		
	}
	private void setID(int estado,int ultimoFinal) {
			ultimoFinal = 3;
			estado = 3 ;
			buffer.marcarUltimo();
	}
	
	private boolean delimitador(int codigo) {
		return codigo ==';'?  true :  false;
	}
	

	

}
