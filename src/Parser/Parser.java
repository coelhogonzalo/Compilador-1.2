//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramaticaGrupo16.y"
package Parser;
import java.io.IOException;


import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Token;
import AnalizadorLexico.TokenValue;
import java.util.ArrayList;
import GeneracionCodigoIntermedio.Polaca_Inversa;
import java.util.Arrays;
//#line 28 "Parser.java"




public class Parser
{

    boolean yydebug;        //do I want debug output?
    int yynerrs;            //number of errors so far
    int yyerrflag;          //was there an error?
    int yychar;             //the current working character

    //########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
    void debug(String msg)
    {
        if (yydebug)
            System.out.println(msg);
    }

    //########## STATE STACK ##########
    final static int YYSTACKSIZE = 500;  //maximum stack size
    int statestk[] = new int[YYSTACKSIZE]; //state stack
    int stateptr;
    int stateptrmax;                     //highest index of stackptr
    int statemax;                        //state when highest index reached
    //###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
    final void state_push(int state)
    {
        try {
            stateptr++;
            statestk[stateptr]=state;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            int oldsize = statestk.length;
            int newsize = oldsize * 2;
            int[] newstack = new int[newsize];
            System.arraycopy(statestk,0,newstack,0,oldsize);
            statestk = newstack;
            statestk[stateptr]=state;
        }
    }
    final int state_pop()
    {
        return statestk[stateptr--];
    }
    final void state_drop(int cnt)
    {
        stateptr -= cnt;
    }
    final int state_peek(int relative)
    {
        return statestk[stateptr-relative];
    }
    //###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
    final boolean init_stacks()
    {
        stateptr = -1;
        val_init();
        return true;
    }
    //###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
    void dump_stacks(int count)
    {
        int i;
        System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
        for (i=0;i<count;i++)
            System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
        System.out.println("======================");
    }


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


    String   yytext;//user variable to return contextual strings
    ParserVal yyval; //used to return semantic vals from action routines
    ParserVal yylval;//the 'lval' (result) I got from yylex()
    ParserVal valstk[];
    int valptr;
    //###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
    void val_init()
    {
        valstk=new ParserVal[YYSTACKSIZE];
        yyval=new ParserVal();
        yylval=new ParserVal();
        valptr=-1;
    }
    void val_push(ParserVal val)
    {
        if (valptr>=YYSTACKSIZE)
            return;
        valstk[++valptr]=val;
    }
    ParserVal val_pop()
    {
        if (valptr<0)
            return new ParserVal();
        return valstk[valptr--];
    }
    void val_drop(int cnt)
    {
        int ptr;
        ptr=valptr-cnt;
        if (ptr<0)
            return;
        valptr = ptr;
    }
    ParserVal val_peek(int relative)
    {
        int ptr;
        ptr=valptr-relative;
        if (ptr<0)
            return new ParserVal();
        return valstk[ptr];
    }
    final ParserVal dup_yyval(ParserVal val)
    {
        ParserVal dup = new ParserVal();
        dup.ival = val.ival;
        dup.dval = val.dval;
        dup.sval = val.sval;
        dup.obj = val.obj;
        return dup;
    }
    //#### end semantic value section ####
    public final static short ID=257;
    public final static short CADENA=258;
    public final static short USLINTEGER=259;
    public final static short SINGLE=260;
    public final static short IF=261;
    public final static short ELSE=262;
    public final static short ENDIF=263;
    public final static short WHILE=264;
    public final static short READONLY=265;
    public final static short WRITE=266;
    public final static short PASS=267;
    public final static short MAYORIGUAL=268;
    public final static short MENORIGUAL=269;
    public final static short IGUALIGUAL=270;
    public final static short DISTINTO=271;
    public final static short ASIGN=272;
    public final static short RETURN=273;
    public final static short PRINT=274;
    public final static short SINGLEPR=275;
    public final static short USLINTEGERPR=276;
    public final static short INVALIDO=277;
    public final static short YYERRCODE=256;
    final static short yylhs[] = {                           -1,
            0,    1,    1,    2,    2,    4,    4,    7,    8,    9,
            10,   10,   12,   12,   13,   13,   11,    6,    6,   15,
            15,   16,   16,   14,   18,   19,   18,    3,    3,    3,
            3,    3,   21,   22,   23,   25,   24,   26,   27,   27,
            27,   27,   27,   27,   20,   17,   17,   17,   28,   28,
            28,   29,   29,   29,   29,   29,   30,    5,    5,   31,
            31,   31,   31,   31,
    };
    final static short yylen[] = {                            2,
            1,    2,    1,    1,    1,    3,    3,    2,    4,    4,
            2,    1,    1,    1,    3,    3,    2,    3,    1,    3,
            1,    2,    1,    3,    3,    0,    4,    3,    2,    3,
            5,    2,    2,    1,    2,    1,    3,    3,    1,    1,
            1,    1,    1,    1,    3,    3,    3,    1,    3,    3,
            1,    1,    1,    1,    2,    2,    5,    1,    1,    1,
            1,    1,    3,    0,
    };
    final static short yydefred[] = {                         0,
            0,    0,   36,    0,   59,   58,    0,    0,    3,    4,
            5,    0,    0,    0,    0,    0,    0,    0,    0,   33,
            0,    0,    2,    0,    0,    0,    0,   29,    0,   21,
            0,   32,   35,    0,   53,   54,    0,    0,    0,   51,
            0,    0,    0,    0,   28,    6,    0,    0,    0,    7,
            23,    0,   34,   30,    0,    0,   56,   55,    0,    0,
            0,    0,   42,   41,   43,   44,   39,   40,    0,   37,
            26,   25,   18,    0,   13,    0,    0,    0,   12,   14,
            20,   22,    0,    0,    0,    0,   49,   50,    0,   27,
            9,    0,    0,    0,    0,   11,   31,    0,   15,   16,
            0,   17,   10,   60,    0,   62,    0,    0,    0,   57,
            24,   63,
    };
    final static short yydgoto[] = {                          7,
            8,    9,   30,   11,   12,   25,   13,   27,   50,   78,
            95,   79,   80,  102,   31,   52,   38,   22,   90,   14,
            15,   55,   16,   20,   17,   42,   69,   39,   40,   57,
            107,
    };
    final static short yysindex[] = {                      -211,
            -231,    4,    0,   15,    0,    0,    0, -211,    0,    0,
            0, -175,   33,   34, -108, -108,    4,  -34,  -34,    0,
            -210,   46,    0,    0,  -17, -184,  -39,    0, -185,    0,
            -169,    0,    0,   57,    0,    0, -162,   -5,   19,    0,
            -25,   61,   62,   63,    0,    0, -152, -150, -211,    0,
            0, -109,    0,    0, -108, -149,    0,    0,  -34,  -34,
            -34,  -34,    0,    0,    0,    0,    0,    0,  -34,    0,
            0,    0,    0,   68,    0, -175,   33, -205,    0,    0,
            0,    0, -153,   52,   19,   19,    0,    0,   -5,    0,
            0,   16,  -39,   72,  -12,    0,    0, -179,    0,    0,
            -34,    0,    0,    0,   55,    0,   74,   40, -151,    0,
            0,    0,
    };
    final static short yyrindex[] = {                         0,
            0,    0,    0,    0,    0,    0,    0,  117,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,   -1,    0,    0,    0,    0,    0,    0,
            0,    0,    0,  -41,    0,    0,    0,   46,  -36,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
            0,    0,    0,    0,  -31,  -11,    0,    0,   77,    0,
            0,    0,    0,    0,    0,    0,    0,   78,    0,    0,
            0,    0,    0,    0,   79,    0,    0,    0,    0,    0,
            0,    0,
    };
    final static short yygindex[] = {                         0,
            0,  113,   28,    0,   -4,   47,  -24,   45,   31,    0,
            0,   48,    0,    0,    7,    0,   -2,    0,    0,    0,
            0,    0,    0,  108,    0,    0,    0,   36,   39,    0,
            0,
    };
    final static int YYTABLESIZE=260;
    static short yytable[];
    static { yytable();}
    static void yytable(){
        yytable = new short[]{                         52,
                52,   52,   52,   52,   48,   52,   48,   48,   48,   46,
                37,   46,   46,   46,   29,   81,   41,   59,   52,   60,
                52,   48,   32,   48,   77,   48,   46,   10,   46,   47,
                46,   47,   47,   47,   67,   10,   68,   59,    8,   60,
                18,   47,   19,   19,   76,    1,   43,   44,   47,    2,
                47,    1,    3,   77,   21,    2,   51,   19,    3,   99,
                61,   83,    4,    5,    6,   62,   89,   94,    4,    5,
                6,    1,   26,   76,   47,    2,   75,   28,    3,   82,
                111,   24,   59,   49,   60,  104,  105,  106,    4,   45,
                5,    6,   53,   54,   85,   86,   56,   58,  108,   87,
                88,   70,   71,   72,   73,   75,   74,   84,   91,   97,
                98,  101,  103,  109,  110,  112,    1,   38,   64,   61,
                23,   93,   92,  100,   33,   96,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    1,    1,    0,
                0,    2,    2,    0,    3,    3,    0,    0,    0,    0,
                0,    0,    0,    0,    4,    4,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
                0,    0,   34,    0,   35,   36,   52,   52,   52,   52,
                0,   48,   48,   48,   48,    0,   46,   46,   46,   46,
                0,    0,   63,   64,   65,   66,    0,    0,    0,    0,
                0,    0,    0,    0,    0,    0,   47,   47,   47,   47,
        };
    }
    static short yycheck[];
    static { yycheck(); }
    static void yycheck() {
        yycheck = new short[] {                         41,
                42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
                45,   43,   44,   45,  123,  125,   19,   43,   60,   45,
                62,   26,   16,   60,   49,   62,   44,    0,   60,   41,
                62,   43,   44,   45,   60,    8,   62,   43,   40,   45,
                272,   59,   44,   40,   49,  257,  257,  258,   60,  261,
                62,  257,  264,   78,   40,  261,   29,   59,  264,   44,
                42,   55,  274,  275,  276,   47,   69,  273,  274,  275,
                276,  257,   40,   78,   59,  261,   49,   44,  264,   52,
                41,  257,   43,  123,   45,  265,  266,  267,  274,   44,
                275,  276,  262,  263,   59,   60,   40,  260,  101,   61,
                62,   41,   41,   41,  257,   78,  257,  257,   41,  263,
                59,   40,  125,   59,   41,  267,    0,   41,   41,   41,
                8,   77,   76,   93,   17,   78,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  257,   -1,
                -1,  261,  261,   -1,  264,  264,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,  274,  274,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
                -1,   -1,  257,   -1,  259,  260,  268,  269,  270,  271,
                -1,  268,  269,  270,  271,   -1,  268,  269,  270,  271,
                -1,   -1,  268,  269,  270,  271,   -1,   -1,   -1,   -1,
                -1,   -1,   -1,   -1,   -1,   -1,  268,  269,  270,  271,
        };
    }
    final static short YYFINAL=7;
    final static short YYMAXTOKEN=277;
    final static String yyname[] = {
            "end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
            "'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
            "'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            "'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,"ID","CADENA","USLINTEGER","SINGLE","IF",
            "ELSE","ENDIF","WHILE","READONLY","WRITE","PASS","MAYORIGUAL","MENORIGUAL",
            "IGUALIGUAL","DISTINTO","ASIGN","RETURN","PRINT","SINGLEPR","USLINTEGERPR",
            "INVALIDO",
    };
    final static String yyrule[] = {
            "$accept : program",
            "program : BS",
            "BS : BS sentencia",
            "BS : sentencia",
            "sentencia : sentenciaCE",
            "sentencia : sentenciaDEC",
            "sentenciaDEC : tipo lista_variables ','",
            "sentenciaDEC : tipoFunID parametrosDef cuerpofuncion",
            "tipoFunID : tipo ID",
            "parametrosDef : '(' tipo ID ')'",
            "cuerpofuncion : '{' BSFuncion retorno '}'",
            "BSFuncion : BSFuncion sentenciaFuncion",
            "BSFuncion : sentenciaFuncion",
            "sentenciaFuncion : sentenciaCE",
            "sentenciaFuncion : sentenciaDECFuncion",
            "sentenciaDECFuncion : tipo lista_variables ','",
            "sentenciaDECFuncion : tipoFunID parametrosDef cuerpofuncion",
            "retorno : RETURN expresioncparentesis",
            "lista_variables : lista_variables ';' ID",
            "lista_variables : ID",
            "BCE : '{' BCE2 '}'",
            "BCE : sentenciaCE",
            "BCE2 : BCE2 sentenciaCE",
            "BCE2 : sentenciaCE",
            "expresioncparentesis : '(' expresion ')'",
            "printeable : '(' CADENA ')'",
            "$$1 :",
            "printeable : '(' ID ')' $$1",
            "sentenciaCE : PRINT printeable ','",
            "sentenciaCE : asignacion ','",
            "sentenciaCE : ifcond BCE ENDIF",
            "sentenciaCE : ifcond BCE elsecond BCE ENDIF",
            "sentenciaCE : whilecond BCE",
            "ifcond : IF condicioncparentesis",
            "elsecond : ELSE",
            "whilecond : whileparaapilar condicioncparentesis",
            "whileparaapilar : WHILE",
            "condicioncparentesis : '(' condicion ')'",
            "condicion : expresion operador_logico expresion",
            "operador_logico : '<'",
            "operador_logico : '>'",
            "operador_logico : MENORIGUAL",
            "operador_logico : MAYORIGUAL",
            "operador_logico : IGUALIGUAL",
            "operador_logico : DISTINTO",
            "asignacion : ID ASIGN expresion",
            "expresion : expresion '+' termino",
            "expresion : expresion '-' termino",
            "expresion : termino",
            "termino : termino '*' factor",
            "termino : termino '/' factor",
            "termino : factor",
            "factor : ID",
            "factor : USLINTEGER",
            "factor : SINGLE",
            "factor : '-' SINGLE",
            "factor : ID parametros",
            "parametros : '(' ID ';' lista_permisos ')'",
            "tipo : USLINTEGERPR",
            "tipo : SINGLEPR",
            "lista_permisos : READONLY",
            "lista_permisos : WRITE",
            "lista_permisos : PASS",
            "lista_permisos : WRITE ';' PASS",
            "lista_permisos :",
    };

//#line 314 "gramaticaGrupo16.y"

    public Polaca_Inversa PI = new Polaca_Inversa();
    public Analizador_Lexico al;
    public ArrayList<AnalizadorLexico.Error> errores;
    public static TokenValue ultimoTokenleido;
    public static ArrayList<String> estructuras;
    public String idFun;
    public String idParam;


    public int yylex(){
        Token t=null;
        try {
            t = this.al.getToken();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("IOException  en el metodo getToken");
        }
        if(t!=null){
            yylval = new ParserVal(t.lexema);
            TokenValue tv = new TokenValue (t.lexema, Analizador_Lexico.cantLN);
            ultimoTokenleido=tv;
            //System.out.println("leyo : "+t.lexema+" 	Numero de token: "+t.nro);
            return t.nro;
        }
        return 0;
    }

    public void yyerror ( String e){
        System.out.println(e);
    }
    public int parsepublico(){
        return this.yyparse();
    }
    public void registrarTipo(String listaVariables,String tipo){
        ArrayList<String> items= new ArrayList<String>(Arrays.asList(listaVariables.split(" ")));
        for (String item:items){
            //System.out.println(items.size()+" El identificador es :'"+item+"'");
            Token t=Analizador_Lexico.tablaSimbolos.get(item);
            t.tipo=tipo;
        }
	/*while(pos<listaVariables.length()){
		char nuevoChar=listaVariables.charAt(pos);
		if(nuevoChar!=' '){
			idVariable=idVariable+nuevoChar;
			System.out.println(idVariable);
		}
		else{
			Token t=Analizador_Lexico.tablaSimbolos.get(idVariable);
			t.tipo=tipo;
			idVariable="";
		}
	}*/
    }
    public static boolean  isPermited(String permisoFuncion,String permisoInvocacion){
        if(permisoFuncion.equals(permisoInvocacion))
            return true;
        if(permisoInvocacion.length()>5)
            if(permisoFuncion.equals(permisoInvocacion.substring(0, 5)))//Se fija si permisoInvocacion tiene un write y permisoFuncion tambien lo tiene
                return true;
        if(permisoInvocacion.length()>6)
            if(permisoFuncion.equals(permisoInvocacion.substring(6)))//Se fija si permisoInvocacion tiene un pass y permisoFuncion tambien lo tiene
                return true;
        if(permisoFuncion=="noseusaelparametro")
            return true;
        if(permisoFuncion!="readonly"&&permisoInvocacion=="readonly")
            return true;
        return false;
    }  /* Lo comento asi no genera codigo
    public static void testing_isPermited() {
    	System.out.println("");
    	System.out.println("Testing con readonly en la funcion:");
    	System.out.println("");
    	if(isPermited("readonly","readonly"))
    		System.out.println("Recibi un readonly y la funcion tenia un readonly, lo acepte");
    	if(!isPermited("readonly","pass"))
    		System.out.println("Recibi un pass y la funcion tenia readonly, RECHAZADO");
    	if(!isPermited("readonly","write"))
    		System.out.println("Recibi un write y la funcion tenia readonly, RECHAZADO");
    	if(!isPermited("readonly","write;pass"))
    		System.out.println("Recibi un write;pass y la funcion tenia readonly, RECHAZADO");
    	System.out.println("");
    	System.out.println("Testing con pass en la funcion:");
    	System.out.println("");
    	if(isPermited("pass","pass"))
    		System.out.println("Recibi un pass y la funcion tenia un pass, lo acepte");
    	if(isPermited("pass","readonly"))
    		System.out.println("Recibi un readonly y la funcion tenia pass, lo acepte");
    	if(!isPermited("pass","write"))
    		System.out.println("Recibi un write y la funcion tenia pass, RECHAZADO");
    	if(isPermited("pass","write;pass"))
    		System.out.println("Recibi un write;pass y la funcion tenia pass, lo acepte");
    	System.out.println("");
    	System.out.println("Testing con write en la funcion:");
    	System.out.println("");
    	if(isPermited("write","write"))
    		System.out.println("Recibi un write y la funcion tenia write, lo acepte");
    	if(!isPermited("write","pass"))
    		System.out.println("Recibi un pass y la funcion tenia un write, RECHAZADO");
    	if(isPermited("write","readonly"))
    		System.out.println("Recibi un readonly y la funcion tenia write, lo acepte");
    	if(isPermited("write","write;pass"))
    		System.out.println("Recibi un write;pass y la funcion tenia write, lo acepte");
    	System.out.println("");
    	System.out.println("Testing con write;pass en la funcion:");
    	System.out.println("");
    	if(!isPermited("write;pass","write"))
    		System.out.println("Recibi un write y la funcion tenia write;pass, RECHAZADO");
    	if(!isPermited("write;pass","pass"))
    		System.out.println("Recibi un pass y la funcion tenia un write;pass, RECHAZADO");
    	if(isPermited("write;pass","readonly"))
    		System.out.println("Recibi un readonly y la funcion tenia write;pass, lo acepte");
    	if(isPermited("write;pass","write;pass"))
    		System.out.println("Recibi un write;pass y la funcion tenia write;pass, lo acepte");
    	System.out.println("");
    	System.out.println("Testing con noseusaelparametro en la funcion:");
    	System.out.println("");
    	if(isPermited("noseusaelparametro","readonly"))
    		System.out.println("Recibi un readonly y la funcion tenia un noseusaelparametro, lo acepte");
    	if(isPermited("noseusaelparametro","pass"))
    		System.out.println("Recibi un pass y la funcion tenia noseusaelparametro, lo acepte");
    	if(isPermited("noseusaelparametro","write"))
    		System.out.println("Recibi un write y la funcion tenia noseusaelparametro, lo acepte");
    	if(isPermited("noseusaelparametro","write;pass"))
    		System.out.println("Recibi un write;pass y la funcion tenia noseusaelparametro, lo acepte");
    }*/




    //#line 480 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
    void yylexdebug(int state,int ch)
    {
        String s=null;
        if (ch < 0) ch=0;
        if (ch <= YYMAXTOKEN) //check index bounds
            s = yyname[ch];    //now get it
        if (s==null)
            s = "illegal-symbol";
        debug("state "+state+", reading "+ch+" ("+s+")");
    }





    //The following are now global, to aid in error reporting
    int yyn;       //next next thing to do
    int yym;       //
    int yystate;   //current parsing state from state table
    String yys;    //current token string


    //###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
    int yyparse()
    {
        boolean doaction;
        init_stacks();
        yynerrs = 0;
        yyerrflag = 0;
        yychar = -1;          //impossible char forces a read
        yystate=0;            //initial state
        state_push(yystate);  //save it
        val_push(yylval);     //save empty value
        while (true) //until parsing is done, either correctly, or w/error
        {
            doaction=true;
            if (yydebug) debug("loop");
            //#### NEXT ACTION (from reduction table)
            for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
            {
                if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
                if (yychar < 0)      //we want a char?
                {
                    yychar = yylex();  //get next token
                    if (yydebug) debug(" next yychar:"+yychar);
                    //#### ERROR CHECK ####
                    if (yychar < 0)    //it it didn't work/error
                    {
                        yychar = 0;      //change it to default string (no -1!)
                        if (yydebug)
                            yylexdebug(yystate,yychar);
                    }
                }//yychar<0
                yyn = yysindex[yystate];  //get amount to shift by (shift index)
                if ((yyn != 0) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
                {
                    if (yydebug)
                        debug("state "+yystate+", shifting to state "+yytable[yyn]);
                    //#### NEXT STATE ####
                    yystate = yytable[yyn];//we are in a new state
                    state_push(yystate);   //save it
                    val_push(yylval);      //push our lval as the input for next rule
                    yychar = -1;           //since we have 'eaten' a token, say we need another
                    if (yyerrflag > 0)     //have we recovered an error?
                        --yyerrflag;        //give ourselves credit
                    doaction=false;        //but don't process yet
                    break;   //quit the yyn=0 loop
                }

                yyn = yyrindex[yystate];  //reduce
                if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
                {   //we reduced!
                    if (yydebug) debug("reduce");
                    yyn = yytable[yyn];
                    doaction=true; //get ready to execute
                    break;         //drop down to actions
                }
                else //ERROR RECOVERY
                {
                    if (yyerrflag==0)
                    {
                        yyerror("syntax error");
                        yynerrs++;
                    }
                    if (yyerrflag < 3) //low error count?
                    {
                        yyerrflag = 3;
                        while (true)   //do until break
                        {
                            if (stateptr<0)   //check for under & overflow here
                            {
                                yyerror("stack underflow. aborting...");  //note lower case 's'
                                return 1;
                            }
                            yyn = yysindex[state_peek(0)];
                            if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
                            {
                                if (yydebug)
                                    debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
                                yystate = yytable[yyn];
                                state_push(yystate);
                                val_push(yylval);
                                doaction=false;
                                break;
                            }
                            else
                            {
                                if (yydebug)
                                    debug("error recovery discarding state "+state_peek(0)+" ");
                                if (stateptr<0)   //check for under & overflow here
                                {
                                    yyerror("Stack underflow. aborting...");  //capital 'S'
                                    return 1;
                                }
                                state_pop();
                                val_pop();
                            }
                        }
                    }
                    else            //discard this token
                    {
                        if (yychar == 0)
                            return 1; //yyabort
                        if (yydebug)
                        {
                            yys = null;
                            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                            if (yys == null) yys = "illegal-symbol";
                            debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
                        }
                        yychar = -1;  //read another
                    }
                }//end error recovery
            }//yyn=0 loop
            if (!doaction)   //any reason not to proceed?
                continue;      //skip action
            yym = yylen[yyn];          //get count of terminals on rhs
            if (yydebug)
                debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
            if (yym>0)                 //if count of rhs not 'nil'
                yyval = val_peek(yym-1); //get current semantic value
            yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
            switch(yyn)
            {
//########## USER-SUPPLIED ACTIONS ##########
                case 2:
//#line 25 "gramaticaGrupo16.y"
                {
                    if ( isPermited(val_peek(1).sval, val_peek(0).sval) ){
                        yyval.sval = val_peek(0).sval;
                    }else
                        yyval.sval = val_peek(1).sval;
                }
                break;
                case 3:
//#line 31 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 4:
//#line 35 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 5:
//#line 36 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 6:
//#line 41 "gramaticaGrupo16.y"
                { yyval.sval = "noseusaelparametro"; registrarTipo( val_peek(1).sval, val_peek(2).sval); /*System.out.println($2.sval);*/
                    Parser.estructuras.add("Se detecto la declaracion de variables en la linea "+Analizador_Lexico.cantLN+"\r\n");}
                break;
                case 7:
//#line 43 "gramaticaGrupo16.y"
                { Analizador_Lexico.tablaSimbolos.get(val_peek(2).sval).permisoFun = val_peek(0).sval; Parser.estructuras.add("Se detecto la declaracion de una funcion en la linea "+Analizador_Lexico.cantLN+"\r\n");}
                break;
                case 8:
//#line 54 "gramaticaGrupo16.y"
                { this.idFun = val_peek(0).sval; Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(0).sval);
                    if(t.declarada){
                        this.errores.add(new ErrorG("Error SIN NUMERO: El identificador '"+t.lexema+"' ,de uso '"+t.uso+"' ya esta declarado", Analizador_Lexico.cantLN));
                        PI.inicioFuncion(val_peek(0).sval);
                    }
                    else{
                        t.declarada=true;
                        PI.inicioFuncion(val_peek(0).sval);
                        t.uso="funcion";
                        t.tipo=val_peek(1).sval;
                    }
                }
                break;
                case 9:
//#line 69 "gramaticaGrupo16.y"
                { PI.paramFun(val_peek(1).sval);
                    Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(1).sval);
                    if(t!=null){
                        t.uso="parametro";
                        t.declarada=true;
                    }
                    else
                        System.out.println("El token que quisiste recuperar es null (ndmpp)");
                    this.idParam = val_peek(1).sval; }
                break;
                case 10:
//#line 82 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(2).sval; }
                break;
                case 11:
//#line 88 "gramaticaGrupo16.y"
                {
                    if ( isPermited(val_peek(1).sval, val_peek(0).sval) )
                        yyval.sval = val_peek(0).sval;
                    else
                        yyval.sval = val_peek(1).sval;
                }
                break;
                case 12:
//#line 94 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 13:
//#line 96 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 14:
//#line 97 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 15:
//#line 99 "gramaticaGrupo16.y"
                { yyval.sval = "noseusaelparametro"; registrarTipo( val_peek(1).sval, val_peek(2).sval); /*System.out.println($2.sval);*/
                    Parser.estructuras.add("Se detecto la declaracion de variables en la linea "+Analizador_Lexico.cantLN+"\r\n");}
                break;
                case 16:
//#line 103 "gramaticaGrupo16.y"
                { this.errores.add(new ErrorG("Error SIN NUMERO: Se declar? una funcion dentro de otra funcion", Analizador_Lexico.cantLN));}
                break;
                case 17:
//#line 110 "gramaticaGrupo16.y"
                {PI.finFuncion(); }
                break;
                case 18:
//#line 115 "gramaticaGrupo16.y"
                {Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(0).sval);
                    if(t!=null){
                        if(t.declarada==false&&t.uso!="parametro"){
                            t.declarada=true;
                            t.uso="variable";
                        }
                        else
                            this.errores.add(new ErrorG("Error SIN NUMERO: Se redeclaro el identificador de uso "+t.uso+" :'"+t.lexema+"' ", Analizador_Lexico.cantLN));
                        yyval=new ParserVal(yyval.sval+" "+val_peek(0).sval);
                    }
                    else
                        System.out.println("El token que quisiste recuperar es null");}
                break;
                case 19:
//#line 127 "gramaticaGrupo16.y"
                {	Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(0).sval);
                    if(t!=null){
                        if(t.declarada==false&&t.uso!="parametro"){
                            t.declarada=true;
                            t.uso="variable";
                        }
                        else
                            this.errores.add(new ErrorG("Error SIN NUMERO: Se redeclaro el identificador de tipo "+t.uso+" :'"+t.lexema+"' ", Analizador_Lexico.cantLN));
                        yyval=new ParserVal(yyval.sval+" "+val_peek(0).sval);
                    }
                    else
                        System.out.println("El token que quisiste recuperar es null");
                }
                break;
                case 25:
//#line 159 "gramaticaGrupo16.y"
                { PI.put(val_peek(1).sval); }
                break;
                case 26:
//#line 160 "gramaticaGrupo16.y"
                { if ( val_peek(1).sval == this.idParam )
                    yyval.sval = "readonly";
                else
                    yyval.sval = "noseusaelparametro";
                    PI.put(val_peek(1).sval); }
                break;
                case 27:
//#line 164 "gramaticaGrupo16.y"
                {System.out.println("--------------------------------------------------------------------------------------------");
                    System.out.println("DEBUGUEANDO: esto es un separador");
                    System.out.println("--------------------------------------------------------------------------------------------");}
                break;
                case 28:
//#line 171 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(1).sval; Parser.estructuras.add("Se detecto un print en la linea "+Analizador_Lexico.cantLN+"\r\n"); PI.put("print");}
                break;
                case 29:
//#line 172 "gramaticaGrupo16.y"
                {  yyval.sval = val_peek(1).sval; Parser.estructuras.add("Se detecto una asignacion en  la linea "+Analizador_Lexico.cantLN+"\r\n");}
                break;
                case 30:
//#line 173 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(1).sval; Parser.estructuras.add("Se detecto un if en la linea "+Analizador_Lexico.cantLN+"\r\n"); PI.desapilar(); }
                break;
                case 31:
//#line 174 "gramaticaGrupo16.y"
                { if ( isPermited(val_peek(3).sval, val_peek(1).sval) ) yyval.sval = val_peek(1).sval; Parser.estructuras.add("Se detecto un if en la linea "+Analizador_Lexico.cantLN+"\r\n"); PI.desapilar();}
                break;
                case 32:
//#line 175 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; Parser.estructuras.add("Se detecto un while en la linea "+Analizador_Lexico.cantLN+"\r\n"); PI.saltoIncond(); PI.desapilar(); }
                break;
                case 33:
//#line 186 "gramaticaGrupo16.y"
                { PI.bifurcacion(); }
                break;
                case 34:
//#line 190 "gramaticaGrupo16.y"
                { PI.desapilarElse(); PI.bifurcacionElse(); }
                break;
                case 35:
//#line 194 "gramaticaGrupo16.y"
                { PI.bifurcacion(); }
                break;
                case 36:
//#line 198 "gramaticaGrupo16.y"
                { PI.setSaltoIncond(); }
                break;
                case 38:
//#line 213 "gramaticaGrupo16.y"
                { PI.put(val_peek(1).sval); }
                break;
                case 39:
//#line 220 "gramaticaGrupo16.y"
                { yyval.sval = "<"; }
                break;
                case 40:
//#line 221 "gramaticaGrupo16.y"
                { yyval.sval = ">"; }
                break;
                case 41:
//#line 222 "gramaticaGrupo16.y"
                { yyval.sval = "<="; }
                break;
                case 42:
//#line 223 "gramaticaGrupo16.y"
                { yyval.sval = ">="; }
                break;
                case 43:
//#line 224 "gramaticaGrupo16.y"
                { yyval.sval = "=="; }
                break;
                case 44:
//#line 225 "gramaticaGrupo16.y"
                { yyval.sval = "!="; }
                break;
                case 45:
//#line 229 "gramaticaGrupo16.y"
                {
                    if ( val_peek(2).sval == this.idParam ) {
                        if ( isPermited("write", val_peek(0).sval) )
                            yyval.sval = val_peek(0).sval;
                    }
                    else {
                        if ( isPermited("noseusaelparametro", val_peek(0).sval) )
                            yyval.sval = val_peek(0).sval;
                    }
                    Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(2).sval); PI.put(val_peek(2).sval); PI.put(":=");
                    if(t!=null){
                        if(t.declarada==false)
                            this.errores.add(new ErrorG("Error 34 : La variable "+val_peek(2).sval+" no esta declarada ", Analizador_Lexico.cantLN));
                    }
                    else
                        System.out.println("El identificador "+val_peek(2).sval+" no se agrego a la tabla de simbolos (ndmpp)");
                }
                break;
                case 46:
//#line 251 "gramaticaGrupo16.y"
                { if ( isPermited(val_peek(2).sval, val_peek(0).sval) ) yyval.sval = val_peek(0).sval; else         yyval.sval = val_peek(2).sval ; PI.put("+"); }
                break;
                case 47:
//#line 252 "gramaticaGrupo16.y"
                { if ( isPermited(val_peek(2).sval, val_peek(0).sval) ) yyval.sval = val_peek(0).sval; else         yyval.sval = val_peek(2).sval ; PI.put("-"); }
                break;
                case 48:
//#line 253 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 49:
//#line 256 "gramaticaGrupo16.y"
                { if ( isPermited(val_peek(2).sval, val_peek(0).sval) ) yyval.sval = val_peek(0).sval; else         yyval.sval = val_peek(2).sval ; PI.put("*"); }
                break;
                case 50:
//#line 257 "gramaticaGrupo16.y"
                { if ( isPermited(val_peek(2).sval, val_peek(0).sval) ) yyval.sval = val_peek(0).sval; else         yyval.sval = val_peek(2).sval ; PI.put("/"); }
                break;
                case 51:
//#line 258 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(0).sval; }
                break;
                case 52:
//#line 261 "gramaticaGrupo16.y"
                { if ( idParam == val_peek(0).sval) yyval.sval = "readonly"; else yyval.sval = "noseusaelparametro"; PI.put(val_peek(0).sval); }
                break;
                case 53:
//#line 262 "gramaticaGrupo16.y"
                { yyval.sval = "noseusaelparametro"; PI.put(val_peek(0).sval); }
                break;
                case 54:
//#line 263 "gramaticaGrupo16.y"
                { yyval.sval = "noseusaelparametro"; PI.put(val_peek(0).sval); }
                break;
                case 55:
//#line 264 "gramaticaGrupo16.y"
                {	Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(0).sval);
                    t.lexema="-"+t.lexema; PI.put("-" + val_peek(1).sval);}
                break;
                case 56:
//#line 266 "gramaticaGrupo16.y"
                { System.out.println(Analizador_Lexico.tablaSimbolos.get(val_peek(1).sval).permisoFun + " versus " + val_peek(0).sval);
                    if ( !isPermited(Analizador_Lexico.tablaSimbolos.get(val_peek(1).sval).permisoFun, val_peek(0).sval) )
                        new ErrorG("Error asignacion de permisos", Analizador_Lexico.cantLN);
                    else{
                        /*System.out.println("Permiso aceptado");*/
                    }
                    PI.jumpToFun(val_peek(1).sval); Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(1).sval);
                    if(t!=null){
                        if(t.declarada==false)
                            this.errores.add(new ErrorG("Error 34.6 : La funcion "+val_peek(1).sval+" no esta declarada ", Analizador_Lexico.cantLN));
                        else
                        if(t.uso!="funcion")
                            this.errores.add(new ErrorG("Error 34.7 : El identificador "+t.lexema+" no es una funcion. ", Analizador_Lexico.cantLN));
                    }
                    else
                        System.out.println("El identificador "+val_peek(1).sval+" no se agrego a la tabla de simbolos (El identificador es una funcion) (ndmpp)"); }
                break;
                case 57:
//#line 284 "gramaticaGrupo16.y"
                { yyval.sval = val_peek(1).sval; PI.put(val_peek(3).sval);
                    Token t=Analizador_Lexico.tablaSimbolos.get(val_peek(3).sval);
                    if(t!=null){
                        if(t.declarada==false)
                            this.errores.add(new ErrorG("Error 35: La variable "+val_peek(3).sval+" no esta declarada ", Analizador_Lexico.cantLN));
                    }
                    else
                        System.out.println("El identificador "+val_peek(3).sval+" no se agrego a la tabla de simbolos (ndmpp)");
                }
                break;
//#line 929 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
            }//switch
            //#### Now let's reduce... ####
            if (yydebug) debug("reduce");
            state_drop(yym);             //we just reduced yylen states
            yystate = state_peek(0);     //get new state
            val_drop(yym);               //corresponding value drop
            yym = yylhs[yyn];            //select next TERMINAL(on lhs)
            if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
            {
                if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
                yystate = YYFINAL;         //explicitly say we're done
                state_push(YYFINAL);       //and save it
                val_push(yyval);           //also save the semantic value of parsing
                if (yychar < 0)            //we want another character?
                {
                    yychar = yylex();        //get next character
                    if (yychar<0) yychar=0;  //clean, if necessary
                    if (yydebug)
                        yylexdebug(yystate,yychar);
                }
                if (yychar == 0)          //Good exit (if lex returns 0 ;-)
                    break;                 //quit the loop--all DONE
            }//if yystate
            else                        //else not done yet
            {                         //get next state and push, for next yydefred[]
                yyn = yygindex[yym];      //find out where to go
                if ((yyn != 0) && (yyn += yystate) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
                    yystate = yytable[yyn]; //get new state
                else
                    yystate = yydgoto[yym]; //else go to new defred
                if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
                state_push(yystate);     //going again, so push state & val...
                val_push(yyval);         //for next action
            }
        }//main loop
        return 0;//yyaccept!!
    }
//## end of method parse() ######################################



//## run() --- for Thread #######################################
    /**
     * A default run method, used for operating this parser
     * object in the background.  It is intended for extending Thread
     * or implementing Runnable.  Turn off with -Jnorun .
     */
    public void run()
    {
        yyparse();
    }
//## end of method run() ########################################



//## Constructors ###############################################
    /**
     * Default constructor.  Turn off with -Jnoconstruct .

     */
    public Parser()
    {
        //nothing to do
    }


    /**
     * Create a parser, setting the debug to true or false.
     * @param debugMe true for debugging, false for no debug.
     */
    public Parser(boolean debugMe)
    {
        yydebug=debugMe;
    }
//###############################################################



}
//################### END OF CLASS ##############################
