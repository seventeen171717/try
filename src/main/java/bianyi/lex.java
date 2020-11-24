package bianyi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class lex{
	
	//单词符号类别定义，与实验的表格要求一致
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
	
	//单词类定义二元组:（单词类别, 单词名）
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
	变量说明:
	 line		从终端读入的字符串;	      当前所指位置在计数器 iCurPos, 字符为ch
	 token		正在识别的单词字符串；当前所指位置在计数器 iIndex
	 sym		每个单词符号种类，来源于symbol, 例：symbol.number
	 keyword	保留字表 , 包括:begin, do, end , if, then, while(已排好序）
	 
	 Symlist 	识别出的符号表，每个元素是一个单词的二元组（sym, token)
	***************************************************************************/
	String line; 												
	int iCurPos=0; 												
	char[] token; 	
	int iIndex = 0;	
	public static int Err=0;
	symbol sym;
	String[] keyword={"begin","do","end","if","then","while"};	
	ArrayList<aWord> Symlist;									
	  
	//从终端读入一行程序		
	public String getProgram()
	{		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		line ="";
		try
		{
			do 
			{
				System.out.print("请读入程序串，以. 结束 ：");
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
	
	//增加一个单词到符号表里
	public void AddaWordtoList()
	{
		aWord aWord; 
		aWord=new aWord((new String(token)).trim(),sym);
		Symlist.add(aWord);
	}
	
	//Main函数：主入口
	public static void main(String[] args) {
			
		lex lex = new lex();
		
		//读入一行程序串，以.结束
		lex.line= lex.getProgram();
		lex.Symlist  = new ArrayList<aWord>(20);
		lex.Symlist.clear();
			
		//主要操作：识别程序串line中的单词，并输出到符号表Symlist中去。
		while ((lex.iCurPos<lex.line.length()) && (lex.line.charAt(lex.iCurPos) !='.'))
//		while ((lex.iCurPos==0)
		{
				lex.getSym();
//				lex.block();
//				if ((lex.sym==symbol.period)&&(Err==0))
//					System.out.print("success!\n");
				//把单词加到符号表中去
				lex.AddaWordtoList();
		}		
		//把最后的.加到符号表中去
		lex.token =new char[]{'.'};
		lex.sym = symbol.period;
		lex.AddaWordtoList();
			
		//输出单词符号表
		for (int i=0; i<lex.Symlist.size();i++)
		{	
			System.out.print(lex.Symlist.get(i).toString().trim()+" ");
		}		
	 }
		
	
	/*******************功能函数getSym：识别一个单词符号*********************************
	 
		输入：line, iCurpos，当前程序串所指向位置。
			例：line[]="begin x:=9 end."  iCurpos=6时，则当前从x开始识别
		               0123456 
		输出：(单词名token,单词类别sym)，并加入到符号表Symlist中。iCurpos更新到新的位置。
			例：x识别后，Symlist增加("x",21), 同时更新iCurpos=7，为下一次识别作准备。
	
	*************************************************************************************/
	
	public void getSym()
	{			
		iIndex = 0;
		token = new char[20];
		sym = symbol.nil;
		
		char ch=line.charAt(iCurPos++);

		/*********************************************************
		*									   		  
		*************** 	TODO: 单词识别        ***************
		*                                         
		* 只需要做的是：针对不同的字符识别出相应的 token 和 sym 值
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
		
		
		//没有匹配到合适的单词符号，出错处理
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
				System.out.print("Error"+i+": 语句期望begin符号\n");
				break;
			case 11: 
				System.out.print("Error"+i+": 语句期望end符号\n");
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
				System.out.print("Error!赋值语句左部标识符后面应是赋值号':='\n");
				Err++;
				return;
			}
		}
		else{
			System.out.print("错误的句子！\n");
			Err++;
			return; 
		}
		if(sym==symbol.number) {
			System.out.print("你写了数字！\n");
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
			   System.out.print("语法分析出错！请检查是否缺少‘）’\n");
			   Err++;
			   return;
		   }
	   }
	   else{
		   System.out.print("Error!缺少因子\n");
		   Err++;
		   return;
		   }
	}

}
