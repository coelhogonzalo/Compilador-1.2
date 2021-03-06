package AnalizadorLexico;

//UNSIGNED LONG==286
public class AccionSemantica5 implements AccionSemantica {
    //ES PARA CONSTANTES ENTEROS LARGOS SIN SIGNO
    //CHEQUEA RANGO CONSTANTE Y AGREGA A TABLA DE SIMBOLOS, SI SE VA DE RANGO REEMPLAZA CON EL MAYOR DEL RANGO
    //PIDE DEVOLVER EL PAR <ID,PTR>

	public Token ejecutar(StringBuilder buffer, char c) {

        if (buffer.length() > 0) {
            EliminarChars(buffer);
            if (buffer.length() >= 20)
                buffer.replace(0, buffer.length(), buffer.substring(0, 19));
            long auxLong = Long.valueOf(buffer.toString());
            if ((auxLong <= 4294967295l) && (auxLong >= 0)) {
                buffer.append("_ul");
                String lexema = buffer.toString();
                Token unToken = new Token(lexema, AnalizadorLexico.TOKEN_UL, "uslinteger");
                unToken.uso = "constante";
                AnalizadorLexico.tablaSimbolos.put(lexema, unToken);
                return unToken;
            } else {
                Error e = new Error("WARNING", buffer.toString() + ": valor fuera de rango", AnalizadorLexico.cantLN);
                AnalizadorLexico.errores.add(e);
                buffer.delete(0, buffer.length());
                buffer.append("4294967295_ul");
                String lexema = buffer.toString();
                Token unToken = new Token(lexema, AnalizadorLexico.TOKEN_UL, "uslinteger");
                unToken.uso = "constante";
                AnalizadorLexico.tablaSimbolos.put(lexema, unToken);
                return unToken;
            }
        }
        return null;
    }

    private void EliminarChars(StringBuilder b) {
        if (b.length() > 0)
            b.delete(b.indexOf("_u"), b.length());
    }

}