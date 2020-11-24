package bianyi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class lex{
	
	//���ʷ�������壬��ʵ��ı��Ҫ��һ��
	public enum symbol {
		period(".",0),	plus("+",1), 	minus("-",2), 	times("*",3), 		slash("/",4),	
		eql("=",5),		neq("<>",6),	lss("<",7),		leq("<=",8), 		gtr(">",9),	
		geq(">=",10),	lparen("(",11),	rparen(")",12),	semicolon(";",13),	becomes(":=",14),	
		beginsym("begin",15),	endsym("end",16),		ifsym("if",17),		thensym("then",18),		
		whilesym("while",19),	dosym("do",20),			ident("IDENT",21),	number("number",22),
		nil("nil",23), notes("notes",24);
		
		private String strName; 
		private int iIndex;
		private symbol(String name, int index)
		{
			this.strName = name;
			this.iIndex= index;			
		}
	};
	
	//�����ඨ���Ԫ��:���������, ��������
	public class aWord
	{
		String name;
		symbol symtype;		
		private aWord(String name, symbol symtype)
		{
			this.name = name;
			this.symtype= symtype;
		}
		public String toString()
		{
			return "("+ this.symtype.iIndex+","+this.name.trim()+")";
		}
	}
	
	/***************************************************************************
	����˵��:
	 line		���ն˶�����ַ���;	      ��ǰ��ָλ���ڼ����� iCurPos, �ַ�Ϊch
	 token		����ʶ��ĵ����ַ�������ǰ��ָλ���ڼ����� iIndex
	 sym		ÿ�����ʷ������࣬��Դ��symbol, ����symbol.number
	 keyword	�����ֱ� , ����:begin, do, end , if, then, while(���ź���
	 
	 Symlist 	ʶ����ķ��ű�ÿ��Ԫ����һ�����ʵĶ�Ԫ�飨sym, token)
	***************************************************************************/
	String line; 												
	int iCurPos=0; 												
	char[] token; 	
	int iIndex = 0;	
	public static int Err=0;
	symbol sym;
	String[] keyword={"begin","do","end","if","then","while"};	
	ArrayList<aWord> Symlist;									
	  
	//���ն˶���һ�г���		
	public String getProgram()
	{		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		line ="";
		try
		{
			do 
			{
				System.out.print("�������򴮣���. ���� ��");
				line=in.readLine(); 
			} while (line.endsWith(".")== false);
			in.close();
		}
		catch(IOException e)  
        {  
            e.printStackTrace();
        }  
	
		return line;
    }  
	
	//����һ�����ʵ����ű���
	public void AddaWordtoList()
	{
		aWord aWord; 
		aWord=new aWord((new String(token)).trim(),sym);
		Symlist.add(aWord);
	}
	
	//Main�����������
	public static void main(String[] args) {
			
		lex lex = new lex();
		
		//����һ�г��򴮣���.����
		lex.line= lex.getProgram();
		lex.Symlist  = new ArrayList<aWord>(20);
		lex.Symlist.clear();
			
		//��Ҫ������ʶ�����line�еĵ��ʣ�����������ű�Symlist��ȥ��
		while ((lex.iCurPos<lex.line.length()) && (lex.line.charAt(lex.iCurPos) !='.'))
//		while ((lex.iCurPos==0)
		{
				lex.getSym();
//				lex.block();
//				if ((lex.sym==symbol.period)&&(Err==0))
//					System.out.print("success!\n");
				//�ѵ��ʼӵ����ű���ȥ
				lex.AddaWordtoList();
		}		
		//������.�ӵ����ű���ȥ
		lex.token =new char[]{'.'};
		lex.sym = symbol.period;
		lex.AddaWordtoList();
			
		//������ʷ��ű�
		for (int i=0; i<lex.Symlist.size();i++)
		{	
			System.out.print(lex.Symlist.get(i).toString().trim()+" ");
		}		
	 }
		
	
	/*******************���ܺ���getSym��ʶ��һ�����ʷ���*********************************
	 
		���룺line, iCurpos����ǰ������ָ��λ�á�
			����line[]="begin x:=9 end."  iCurpos=6ʱ����ǰ��x��ʼʶ��
		               0123456 
		�����(������token,�������sym)�������뵽���ű�Symlist�С�iCurpos���µ��µ�λ�á�
			����xʶ���Symlist����("x",21), ͬʱ����iCurpos=7��Ϊ��һ��ʶ����׼����
	
	*************************************************************************************/
	
	public void getSym()
	{			
		iIndex = 0;
		token = new char[20];
		sym = symbol.nil;
		
		char ch=line.charAt(iCurPos++);

		/*********************************************************
		*									   		  
		*************** 	TODO: ����ʶ��        ***************
		*                                         
		* ֻ��Ҫ�����ǣ���Բ�ͬ���ַ�ʶ�����Ӧ�� token �� sym ֵ
		*
		*********************************************************/		
		while ((ch==' ')||(ch=='\n'))
		{
			ch=line.charAt(iCurPos++);
		}
		
		if ((ch>='0')&&(ch<='9'))
		{
			token[iIndex++]=ch;
			ch=line.charAt(iCurPos++);
			while (ch>='0' && ch<='9')
			{
				token[iIndex++]=ch;
				ch=line.charAt(iCurPos++);
			}
			sym = symbol.number;
			iCurPos--;
		}
		else if (( ch>='a' && ch<='z' )||(ch>='A' && ch<='Z'))
		{
			while((ch>='a' && ch<='z')||(ch>='A' && ch<='Z')||(ch>='0' && ch<='9'))
			{
				token[iIndex++]=ch;
				ch=line.charAt(iCurPos++);
			}
			iCurPos--;
			sym=symbol.ident;
			String str1=new String(token,0,iIndex);
			if(str1.equals(keyword[0]))
			{
			    sym=symbol.beginsym;
			}
			else if(str1.equals(keyword[2]))
			{
				sym=symbol.endsym;
			}
			else if(str1.equals(keyword[3]))
			{
				sym=symbol.ifsym;
			}
			else if(str1.equals(keyword[4]))
			{
				sym=symbol.thensym;
			}
			else if(str1.equals(keyword[5]))
			{
				sym=symbol.whilesym;
			}
			else if(str1.equals(keyword[1]))
			{
				sym=symbol.dosym;
			}
		}
		
		else switch (ch)
		{
		case ':' :
			token[iIndex++]=ch;
			ch=line.charAt(iCurPos++);
			if(ch=='=') {
				token[iIndex++]=ch; 
				sym=symbol.becomes;
			}
			else
			{
				sym = symbol.nil;
			}
			break;
		case ';':
			 token[iIndex++]=ch;
             sym=symbol.semicolon;
             break; 
		case '<':
			token[iIndex++]=ch;ch=line.charAt(iCurPos++);
			 if(ch=='>')
			 {
				 token[iIndex++]=ch; 
	             sym=symbol.neq;
			 }
			 else if(ch=='=')
			 {
				 token[iIndex++]=ch; 
	             sym=symbol.leq;
			 }
			 else 
			 {
	             sym=symbol.lss;
	             iCurPos--;
			}
			 break;
		case'>':
			 token[iIndex++]=ch;ch=line.charAt(iCurPos++);
            if(ch=='=')
			 {
				 token[iIndex++]=ch;
	             sym=symbol.geq;
			 }
            else 
			 {
	             sym=symbol.gtr;
	             iCurPos--;
			}
            break;
		case '+':
			token[iIndex++]=ch; 
			sym=symbol.plus;
			break;
		case '-':
			token[iIndex++]=ch; 
			sym=symbol.minus;
			break;
		case '*':
			token[iIndex++]=ch;
			sym=symbol.times;
			break;
		case '/':
//			token[iIndex++]=ch;
			 int temp1=iCurPos;
			 ch=line.charAt(iCurPos++); 
//			 iCurPos--;
			 if(ch=='*') {
				char temp = 0;
				while(iCurPos+2<line.length()) {
//					token[iIndex++]=ch;
					temp=line.charAt(iCurPos++);
					ch=line.charAt(iCurPos);
					if(ch=='/'&&temp=='*') {
//						token[iIndex++]=ch;
						break;
					}
				}
				if(ch=='/'&&temp=='*') {
					iCurPos++;
					sym = symbol.notes;
					break;
				}
				else if(iCurPos+1>line.length()) {
					token = new char[20];
					token[0]='/';
					iCurPos = temp1;
					sym = symbol.slash;
					break;
				}
				else {
					iCurPos--;
					token = new char[20];
					token[0]='/';
					iCurPos = temp1;
					sym = symbol.slash;
					break;
				}
			 }else {
				iCurPos--;
				token[iIndex++]=ch;
				iCurPos++;
				iCurPos--;
				token = new char[20];
				token[0]='/';
				iCurPos = temp1;
				sym = symbol.slash;
				break;
			 }
		case '=':
			token[iIndex++]=ch;
			sym=symbol.eql;
			break;
		case '(':
			token[iIndex++]=ch;
            sym=symbol.lparen;
            break;
		case ')':
			token[iIndex++]=ch;
            sym=symbol.lparen;
            break;
		case '.': 
			sym=symbol.period;
		    token[iIndex++]=ch;
		    break;
		default:
			sym=symbol.nil;
			break;
			
		}
		
		
		//û��ƥ�䵽���ʵĵ��ʷ��ţ�������
		if (sym==symbol.nil)
		{
			System.out.println("Error: Position "+iCurPos+ " occur the unexpected char \'"+ ch+"\'.");
		}
	}
	
	public void error(int i)
	{
		switch (i)
		{
			case 1: 
				System.out.print("Error"+i+": �������begin����\n");
				break;
			case 11: 
				System.out.print("Error"+i+": �������end����\n");
				break;
			default:
				break;
		}
		Err++;
	}
	

	public int block()
	{
	  if (sym != symbol.beginsym)
	  {
		error(1);
	  }
	  else
	  {
		getSym();
		statement();
		while (sym == symbol.semicolon)
		{
			getSym();
			statement();
		}
		if (sym != symbol.endsym)
		{
			error(11);		
		}
		getSym();     
	  }
	  return 0;
	}

	public void statement()
	{
		if(sym == symbol.ident)
		{
			getSym();
			if(sym==symbol.becomes)
			{
				getSym();
				expression();
			}
			else{
				System.out.print("Error!��ֵ����󲿱�ʶ������Ӧ�Ǹ�ֵ��':='\n");
				Err++;
				return;
			}
		}
		else{
			System.out.print("����ľ��ӣ�\n");
			Err++;
			return; 
		}
		if(sym==symbol.number) {
			System.out.print("��д�����֣�\n");
			Err++;
			return; 
		}
	}

	public void expression()
	{
	    if(sym==symbol.plus||sym==symbol.minus) {
	    	getSym();
	    	term();
	    }
	    else{
	    	term();
	        while(sym==symbol.plus||sym==symbol.minus) {
	    		getSym();
	    		term();
	    }
	    }
	}
	public void term()
	{
	   if(sym==symbol.times||sym==symbol.slash) {
		   getSym();
		   factor();
	   }
	   else{
		   factor();
		   while(sym==symbol.times||sym==symbol.slash) {
			   getSym();
			   factor();
		}
	   }
	}


	public void factor()
	{
	   if(sym==symbol.ident||sym==symbol.number) {
		   getSym();
	   }
	   else if(sym==symbol.lparen){
		   getSym();
		   expression();
		   if(sym==symbol.rparen) {
			   getSym();
		   }
		   else {
			   System.out.print("�﷨�������������Ƿ�ȱ�١�����\n");
			   Err++;
			   return;
		   }
	   }
	   else{
		   System.out.print("Error!ȱ������\n");
		   Err++;
		   return;
		   }
	}

}
