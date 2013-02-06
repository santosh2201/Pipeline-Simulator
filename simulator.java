/*
compiling and running :
javac simulator.java 
java simulator (static file).txt (Dynamic file).txt
*/
import java.io.*;
import java.util.*;
class node
{
       int  data;
        node next;
}
class node1
{
  String data;
  int index;

}
class cache
{
                int time;
                int i,j;
                int hitl1,hitl2;
                int mis;
                int mis2;
                int  in1[]= new int[512];
		String l1[]=new String[1024];
		int in2[]=new int[2048];
		node1 l2[]=new node1[16384];		
        cache()
        {              		
			for(i=0;i<512;i++)
			{
			        in1[i]=2*i;
			}
			for(i=0;i<2048;i++)
			{
			        in2[i]=8*i;
			}
			for(i=0;i<16384;i++)
			{
			       l2[i]=new node1();
			        l2[i].data="man";
			        l2[i].index=0;
			}
			for(i=0;i<1024;i++)
			{
			        l1[i]="man";
			}
			hitl1=0;
			hitl2=0;
                        mis=0;
                        mis2=0;
            }
        int find(String s)
        {
                 time=0;	
	        String tag1=s.substring(0,18);	          
	        String ins1=s.substring(18,27);	        
	        String tag2=s.substring(0,16);	     
	        String ins2=s.substring(16,27);	     	       
	        int ind2 = Integer.parseInt(ins2, 2);
	        int h=32*(ind2/4);
	        int ind1 = Integer.parseInt(ins1, 2);
	        int p=readl1(ind1,tag1);	        
	       if(p==2)
	       {
	            readl2((h),tag2);
	       }
	        return time;
        }
        int  readl1(int a,String s)
        {
                int z=in1[a];
                int x=1;
                time=time+1;
                if((l1[z]=="man")&&(l1[z+1]=="man"))
                {
                        l1[z]=s;
                       mis++;
                       return 2;
                }
                 if(l1[z].equals(s)==true)
                {
                        hitl1++;
                        return 1;
                       
                }
               else  if((l1[z].equals(s)==false)&&(l1[z+1]=="man"))
                 {
                        l1[z+1]=s;
                       mis++;
                       return 2;
                 }
                else if(l1[z+1].equals(s)==true)
                {
                        hitl1++;                       
                        return 1;
                }
                 
                else if(((l1[z].equals(s)==false)&&(l1[z+1].equals(s)==false))&&((l1[z]!="man")&&(l1[z+1]!="man")))
                {
                        l1[z]=l1[z+1];
                        l1[z]=s;
                        mis++;
                        return 2;
                }
               time=time+1;
               return 1;
             
        }
          void    readl2(int a,String s)
        {

              time= time+8;
              int l=a;
              int g=a;
              int p=0;
              int w=l2[a].index;
              int tar=a;
               for(int i=0;i<8;i++)
              {
                        if(l2[l].data=="man")
                        {
                                time=time+200;
                                l2[l].data=s;
                                p=1;
                                mis2++;
                                break;
                        }
                        else if(l2[l].data.equals(s)==true)
                        {
                               hitl2++;
                                l2[l].index++;
                                        p=1;
                                break;
                        } 
                                         
                        l++;
                        
              }
                for(int k=0;k<8;k++)
                {
                         if((l2[g].data.equals(s)==false)&&(l2[g].data.equals("man")==false))
                        {
                               
                                           
                                       
                                        p=0;
                                        if(w>l2[g].index)
                                        {
                                                w=l2[g].index;
                                                tar=l;
                                        }
                                        
                        }
                 else
                {
                          p=1;
                        break;
                }
                        g++;
                }
             
                              
             if(p==0)
                {
                        l2[tar].data=s;
                        mis2++;
                        l2[tar].index=0;   
                 }
             
              
        }

}






class branchpredictor extends cache {
	int tur[]=new int[1024];
	int gs[]=new int[4096];
	int bm[]=new int[1024] ;
	int i;
	String bhr;
	double correctcounter;
	int count;
	
	public branchpredictor()
	{
		count=0;
		correctcounter=0;
		for( i=0;i<1024;i++)
		{
			bm[i]=2;																							
			tur[i]=2;
			
		}	
		for(i=0;i<4096;i++)
		{
			gs[i]=2;
		}
		bhr="000000";
		
	}
	
	
	String int2binary(int x)
		{
			
			String st=Integer.toBinaryString(x);
			int ln=st.length();
			int l=(12-ln);
			char ret[]=new char[l];
			for(int i=0;i<l;i++)
			{
				ret[i]='0';
			}	
			String s1=new String(ret);
			String s3=s1+st;
			return s3;		
		}

	double tournament_predictor(String pc,int a){
		
		int bimodalpredict=bimodal_predictor(pc,a);
		int gsharepredict=gshare_predictor(pc,a);
		String pc1=pc.substring(2,12);
		int p=Integer.parseInt(pc1,2);
		int mainprediction=0;
		if(tur[p]==0||tur[p]==1){
			mainprediction=bimodalpredict;
			
			
		}
		if(tur[p]==2||tur[p]==3){
			
			mainprediction=gsharepredict;
			
			
		}
		
		
			
		
		
		if(tur[p]==0){
			if(a==bimodalpredict || a==gsharepredict){
				
				tur[p]=0;
			}
			if(a!=bimodalpredict || a!=gsharepredict){
				
				tur[p]=0;
			}
			if(a==bimodalpredict || a!=gsharepredict){
				
				tur[p]=0;
			}
			if(a!=bimodalpredict || a==gsharepredict){
				
				tur[p]=1;
			}
			
		}
		
		
		if(tur[p]==1){
			if(a==bimodalpredict || a==gsharepredict){

				tur[p]=1;
			}
			if(a!=bimodalpredict || a!=gsharepredict){
				tur[p]=1;
			}
			if(a==bimodalpredict || a!=gsharepredict){
				tur[p]=0;
				
			}
			if(a!=bimodalpredict || a==gsharepredict){
				
				tur[p]=2;
			}
			
		}
		
		if(tur[p]==2){
			if(a==bimodalpredict || a==gsharepredict){
				tur[p]=2;
			}
			if(a!=bimodalpredict || a!=gsharepredict){
				tur[p]=2;
			}
			if(a==bimodalpredict || a!=gsharepredict){
				tur[p]=1;
			}
			if(a!=bimodalpredict || a==gsharepredict){
				tur[p]=3;
			}
			
		}
		
		
		if(tur[p]==3){
			if(a==bimodalpredict || a==gsharepredict){
				tur[p]=3;
			}
			if(a!=bimodalpredict || a!=gsharepredict){
				tur[p]=3;
			}
			if(a==bimodalpredict || a!=gsharepredict){
				tur[p]=2;
			}
			if(a!=bimodalpredict || a==gsharepredict){
				tur[p]=3;
			}
			
		}
			
			
		if(mainprediction==a){
			correctcounter=correctcounter+1;
			
		}
		
			return correctcounter;
			
			
	}
	
	
	int bimodal_predictor(String pc,int a){
		String s=pc.substring(2,12);
		int p2=Integer.parseInt(s,2);
		int predict2=0;		
		if(bm[p2]==0 || bm[p2]==1){
        	predict2=0;
        	
        }
		if(bm[p2]==2 || bm[p2]==3){
        	predict2=1;
        	
        	
        	
                }
		if(bm[p2]==0){
        	if(a==predict2){
        		bm[p2]=1;
        	}
        	if(a!=predict2){
        		bm[p2]=0;
        	}
        }
        
        
        if(bm[p2]==1){
        	if(a==predict2){
        		bm[p2]=2;
        	}
        	if(a!=predict2){
        		bm[p2]=0;
        	}
        }
        
        
        
        if(bm[p2]==2){
        	if(a==predict2){
        		bm[p2]=3;
        	}
        	if(a!=predict2){
        		bm[p2]=1;
        	}
        }
        


        
        if(bm[p2]==3){
        	if(a==predict2){
        		bm[p2]=3;
        	}
        	if(a!=predict2){
        		bm[p2]=2;
        	
        }
        }
		
		
		return predict2;
		
		
		
	}

String update_bhr(int a){
		String lol=bhr.substring(0,5);
		if(a==0){
			bhr="0"+lol;
			
		}
		if(a==1){
			bhr="1"+lol;
	
		}
		return bhr;
		
	}
	
	int gshare_predictor(String pc,int a){
		String s1=pc.substring(0,6);
        String s2=pc.substring(6,12);
       
        String s3=xor(s2,bhr);
       
             s3=s1+s3;
            
        int p2=Integer.parseInt(s3,2);
        int predict1=0;
        if(gs[p2]==0 || gs[p2]==1){
        	predict1=0;
        	
        }
        if(gs[p2]==2 || gs[p2]==3){
        	predict1=1;
        	
        }
        
        if(gs[p2]==0){
        	
        	if(a==predict1){
        		
        		gs[p2]=1;
        	}
        	if(a!=predict1){
        		gs[p2]=0;
        	}
        }
        
        
        if(gs[p2]==1){
        	
        	if(a==predict1){
        		
        		gs[p2]=2;
        	}
        	if(a!=predict1){
        		gs[p2]=0;
        	}
        }
              
        
        if(gs[p2]==2){
        	if(a==predict1){
        		gs[p2]=3;
        	}
        	if(a!=predict1){
        		gs[p2]=1;
        	}
        }
        
        
        if(gs[p2]==3){
        	if(a==predict1){
        		gs[p2]=3;
        	}
        	if(a!=predict1){
        		predict1=a;
        		gs[p2]=2;
        	}
        }
       
       bhr=update_bhr(a);
       
       
           return predict1;
          					    
                    		        	
        }
	
		
	
	String xor(String s1,String s2)
	{
	               char ret[]=new char[6];
	              char a1[]=s1.toCharArray();
	               
	               char b2[]=bhr.toCharArray();
	               
	               for(i=0;i<6;i++)
	               {
	                        if(a1[i]==b2[i])
	                        {
	                                ret[i]='0';
	                        }
	                        else if(a1[i]!=b2[i])
	                        {
	                                ret[i]='1';
	                        }
	               }
	               String str=new String(ret);
	               return str;
	}
	

	

}
class hasstable extends  branchpredictor
{
      ;
        node head,temp,check;
        int count,i,m,TIME,tem;
        int TOTAL;
        int branch;
        double e;
       node  arrey[]=new node[2000];
       node din[]=new node[2];
        hasstable()
        {
               for(int i=0;i<2000;i++)
               {
                        arrey[i]=null;
               } 
               for(i=0;i<2;i++)
               {
                        din[i]=new node();
                        temp=din[i];
                        for(i=0;i<3;i++)
                        {
                                if(i==0)
                                {
                                        temp.next=null;
                                }
                                else
                                {
                                        temp.next=new node();
                                        temp=temp.next;
                                }
                        }
               }
               TOTAL=0;
               count=0;  
               TIME=4; 
               branch=0;
        }  
        void insert(String s)
        {
try
{
                  if(s!=null)
                  {
                	String asr[];
			asr=s.split(" ");
			int k=0;
			  arrey[count]=new node();
			  arrey[count].next=null;
			  temp=arrey[count];
		              while(k!=5)
				{
			                if(k==0)
			                        {
			                                int   b=asr[k].length();
			                                char ch[]=new char[b-2];
			                                for(i=0;i<b-2;i++)
			                                {
			                                        ch[i]=asr[k].charAt(i+2);
			                                }
			                                String sk=new String(ch);
			                                tem=Integer.parseInt(sk, 16);
			                                temp.data=tem;
			                        }
			                 else
			                 {
			                        temp.next=new node();
			                        temp.next.next=null;
			                           int tem=Integer.parseInt(asr[k]);
			                        temp.next.data=tem;
			                        temp=temp.next;
			                 }  
			            k++;            
				}
		}	
              count++;
          } catch(Exception e)
        {
                System.out.println("exception ");
        }     
        }
void insdin(String s)
{
try
{
                        int u,l,i1=0,i2=0,te;
                         String dina[],s3="m";
			dina=s.split(" ");						
			  int k;
			 u=i2;
			       temp=din[0]; 
			        for(k=0;k<3;k++)
			        {
			                if(k==0)
			                {			                  
			                        l=dina[k].length(); 
			                      String s1=dina[k].substring(2,l); 
			                       te=Integer.parseInt(s1, 16);
			                      temp.data=te;
			                      i2=search(te);                                               
			                }
			                if(k==1)
			                {
			                            l=dina[1].length();
			                            String s2=dina[1].substring(2,l); 
			                             s3=converter(s2);			                            
			                }
			                if(k==2)
			                {
			                        int pr=Integer.parseInt(dina[k]);
			                        time(i2,u,s3,pr);
			                }
			                temp=temp.next;
			                
			        }
}catch(Exception e)
{
       System.out.println("exception");
}			       
			TOTAL=TOTAL+1;
			
}
	String int2binary(int x)
		{
			
			String st=Integer.toBinaryString(x);
			int ln=st.length();
			int l=(12-ln);
			char ret[]=new char[l];
			for(int i=0;i<l;i++)
			{
				ret[i]='0';
			}	
			String s1=new String(ret);
			String s3=s1+st;
			return s3;		
		}
String converter(String st )
	{		         
			int ln= st.length();
			
		if(ln==7)
		{
			int t=Integer.parseInt(st,16);
			String g=Integer.toBinaryString(t);
			String o1=st.substring(0,1);
			int i3=Integer.parseInt(o1,16);
			String g3=Integer.toBinaryString(i3);
			boolean y=g3.equalsIgnoreCase("0");
			if(y==true)
			{
				g3="0000";
				g=g3+g;
			}
			String g7="0000"+g;
			return g7;
			
		}
		if(ln==8)
		{
			String s1=st.substring(0,1);
			String s2=st.substring(1,8);
			int i1=Integer.parseInt(s1,16);
			int i2=Integer.parseInt(s2,16);
			String g1=Integer.toBinaryString(i1);
			String g2=Integer.toBinaryString(i2);
			boolean x=g1.equalsIgnoreCase("0");
		
			if(x==true)
			{	
				g1="0000";	
			}
		 	String g3=g1+g2;
			return g3;
		}
			return "abc";

	}
void time(int x,int h,String str,int pri)
{
       
                temp=arrey[x];
               int  pc=arrey[x].data;
                int s,z=-1;
                temp=temp.next;               
                check=arrey[h].next;
                z=check.data;                              
                int y=temp.data;
                temp=temp.next;
                int u=temp.data;
                temp=temp.next;
                int v=temp.data;
               
                         if(y==2)
                                  {                                      
                                        if(u==v)
                                        {
                                                if(z==0)
                                                {
                                                         s=stall(h,u);
                                                         TIME=TIME+s;
                                                }
                                        }
                                         else
                                         {
                                                if(z==0)
                                                {
                                                         s=stall(h,u);
                                                         s=s+stall(h,v);
                                                         TIME=TIME+s;
                                               }
                                        }
                                }
                         if(y==0)
                                {                                      
                                                 int tim=find(str);
                                                  TIME=TIME+tim;                                       
                                }
                         if(y==1)
                                {
                                        if(z==0)
                                       {
                                                s=stall(h,u);
                                                TIME=TIME+s;
                                       }
                                                 int tim=find(str);
                                                 TIME=TIME+tim;                                       
                                }
                       
                            if(y==3)
                       {
                                 branch++;
                                String sq=int2binary(pc);
                                double o=e;
                                e= tournament_predictor(sq,pri);
                               if(o==e+1)
                               {
                                         TIME=TIME+2;
                               }
                                 
                       }            
              TIME=TIME+1;
        
}
int   stall(int t,int x)
{
                check=arrey[t];
                                                       
                                check=check.next.next.next.next;
                                int a=check.data;
                               
                                if(x==a)
                                {
                                           return 1;
                                             
                                }
                                else
                                return 0;
                    
              
            
}
int  search(int pc)
{
     
        for(i=0;i<count;i++)
        {
                        if(arrey[i].data==pc)
                        {
                        break;
                        }
        }
        return i;
       
}
}
class simulator
{
	public static void main(String []str)throws IOException 
	{
	BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
	BufferedReader br2;
	Scanner ip=new Scanner(System.in);
	String data;
	
	File fl,f2;
	FileReader fr;
	  hasstable h;
	        h=new hasstable();
	
	for(int t=0;t<2;t++)
	{
	     
	                String name=str[t];
	                    fl=new File(name);
	      	                  if(fl.exists() == true)
	                          {
		                      if(fl.isFile()==true)
		                             {
		        	                       fr= new FileReader(fl);
		        	                      br2=new BufferedReader(fr);
		        	                      if(t==0)
		        	                      {
		        	                                while((data=br2.readLine())!=null)
		        	                                         {		        	                                    
		        	                                                 h.insert(data);   
		        	 		                         }
		        	 		     }
		        	 		     if(t==1)
		        	 		     {
		        	 		              while((data=br2.readLine())!=null)
		        	                                         {		        	                                    
		        	                                                 h.insdin(data);   
		        	 		                         }  
		        	 		     }
		                                 }
		                          else
		                                {
		        	                                System.out.println("enter the file name only");
		                                }
	                       }      
	                else
	                {
		                System.out.println( "the file does not exits");
	                }
	
	}
	
double IPC=(double) h.TOTAL/(double) h.TIME;
System.out.print(+IPC);
double acc=(double)h.mis/((double)h.mis+(double)h.hitl1)*(100);
System.out.print("    "+acc+" %");
double l2mis=(double)h.mis2/((double)h.mis2+(double)h.hitl2)*(100);
System.out.println("    "+l2mis+" %");
double prid=( h.e)*100/((double) h.branch);
System.out.print("    "+prid+" %");
	
}
}
	
