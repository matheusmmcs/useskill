package br.ufpi.analise;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.bcel.generic.ACONST_NULL;

import br.ufpi.models.Action;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.enums.TipoAcaoEnum;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;

public class TestesAlgoritmos {


	public static void main(String[] args) {
		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = emf.createEntityManager();
		TarefaRepositoryImpl tarefaRepositoryImpl = new TarefaRepositoryImpl(entityManager);
		
		
		long[] ids = {21};//{19, 20}; - 3, 4, 5
		for(long id: ids){
			Tarefa tarefa = tarefaRepositoryImpl.find(id);
			for(Fluxo fluxo : tarefa.getFluxos()){
				List<Action> acoes = tarefaRepositoryImpl.getAcoesReais(fluxo.getId());
				for(Action acao : acoes){
					System.out.println(acao);
				}
			}
			//generateLog("TaskLog-EX-New-"+tarefa.getId(), tarefa.getFluxos(TipoConvidado.EXPERT));
			//generateLog("TaskLog-US-New-"+tarefa.getId(), tarefa.getFluxos(TipoConvidado.USER));
		}
		
		//System.out.println(lcs("Matheus", "Artesateste"));
	}
	
	
	
	
	
	public static String lcs(String a, String b) {
	    int[][] lengths = new int[a.length()+1][b.length()+1];
	 
	    // row 0 and column 0 are initialized to 0 already
	 
	    for (int i = 0; i < a.length(); i++)
	        for (int j = 0; j < b.length(); j++)
	            if (a.charAt(i) == b.charAt(j))
	                lengths[i+1][j+1] = lengths[i][j] + 1;
	            else
	                lengths[i+1][j+1] =
	                    Math.max(lengths[i+1][j], lengths[i][j+1]);
	 
	    // read the substring out from the matrix
	    StringBuffer sb = new StringBuffer();
	    for (int x = a.length(), y = b.length();
	         x != 0 && y != 0; ) {
	        if (lengths[x][y] == lengths[x-1][y])
	            x--;
	        else if (lengths[x][y] == lengths[x][y-1])
	            y--;
	        else {
	            assert a.charAt(x-1) == b.charAt(y-1);
	            System.out.println("X:"+(x-1)+", Y:"+(y-1)+", pos.:"+a.charAt(x-1));
	            sb.append(a.charAt(x-1));
	            x--;
	            y--;
	        }
	    }
	 
	    return sb.reverse().toString();
	}
	
	private static void generateLog(String name, List<Fluxo> fluxos){
		SimpleDateFormat dateFormatInit = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatEnd = new SimpleDateFormat("hh:mm:ss.SSS");
		String log = getLogHeader(name);
		//TipoConvidado
		for(Fluxo fluxo : fluxos){
			log += "<trace><string key=\"concept:name\" value=\"Case"+fluxo.getId()+".0\"/>";
			for(Action acao : fluxo.getAcoes()){
				String date = dateFormatInit.format(acao.getsTime())+"T"+dateFormatEnd.format(acao.getsTime())+"+03:00";
				String attrs = (acao.getsId() == null || acao.getsId().equals("")) && (acao.getsName() == null || acao.getsName().equals("")) ? acao.getsClass() : acao.getsId() + "-" + acao.getsName();
				String element = acao.getsActionType()+"-"+acao.getsTag()+"-"+acao.getsTagIndex()+"-"+ attrs;
				
				log += "<event>"
							+ "<string key=\"org:resource\" value=\"UNDEFINED\"/>"
							+ "<string key=\"id\" value=\""+acao.getId()+"\"/>"
							+ "<date key=\"time:timestamp\" value=\""+date+"\"/>"
							+ "<string key=\"concept:name\" value=\""+element+"\"/>"
							+ "<string key=\"content\" value=\""+(acao.getsContent().length() < 300 ? acao.getsContent().replaceAll("&lt;", "(").replaceAll("&gt;", ")").replaceAll("&quot;", "").replaceAll("&", "").trim() : "null")+"\"/>"
							+ "<string key=\"location\" value=\""+acao.getsUrl()+"\"/>"
							+ "<string key=\"position\" value=\""+acao.getsPosX()+":"+acao.getsPosY()+"\"/>"
							+ "<string key=\"lifecycle:transition\" value=\""+fluxo.getTipoConvidado()+"\"/>"
						+ "</event>";
			}
			log += "</trace>";
		}
		log += "</log>";
		
		FileWriter arquivo;  
        try {  
            arquivo = new FileWriter(new File("C:/"+name+".xes"));  
            arquivo.write(log);  
            arquivo.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	
	private static String getLogHeader(String name){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
				+ "<!-- This file has been generated with the OpenXES library. It conforms -->"
				+ "<!-- to the XML serialization of the XES standard for log storage and -->"
				+ "<!-- management. -->"
				+ "<!-- XES standard version: 1.0 -->"
				+ "<!-- OpenXES library version: 1.0RC7 -->"
				+ "<!-- OpenXES is available from http://www.openxes.org/ -->"
				+ "<log xes.version=\"1.0\" xes.features=\"nested-attributes\" openxes.version=\"1.0RC7\" xmlns=\"http://www.xes-standard.org/\">"
				+ "<extension name=\"Lifecycle\" prefix=\"lifecycle\" uri=\"http://www.xes-standard.org/lifecycle.xesext\"/>"
				+ "<extension name=\"Organizational\" prefix=\"org\" uri=\"http://www.xes-standard.org/org.xesext\"/>"
				+ "<extension name=\"Time\" prefix=\"time\" uri=\"http://www.xes-standard.org/time.xesext\"/>"
				+ "<extension name=\"Concept\" prefix=\"concept\" uri=\"http://www.xes-standard.org/concept.xesext\"/>"
				+ "<extension name=\"Semantic\" prefix=\"semantic\" uri=\"http://www.xes-standard.org/semantic.xesext\"/>"
				+ "<global scope=\"trace\">"
				+ "<string key=\"concept:name\" value=\"__INVALID__\"/>"
				+ "<string key=\"concept:value\" value=\"__INVALID__\"/>"
				+ "</global>"
				+ "<global scope=\"event\">"
				+ "<string key=\"concept:name\" value=\"__INVALID__\"/>"
				+ "<string key=\"lifecycle:transition\" value=\"complete\"/>"
				+ "</global>"
				+ "<classifier name=\"MXML Legacy Classifier\" keys=\"concept:name lifecycle:transition\"/>"
				+ "<classifier name=\"Event Name\" keys=\"concept:name\"/>"
				+ "<classifier name=\"Resource\" keys=\"org:resource\"/>"
				+ "<string key=\"source\" value=\"Rapid Synthesizer\"/>"
				+ "<string key=\"concept:name\" value=\""+name+".mxml\"/>"
				+ "<string key=\"lifecycle:model\" value=\"standard\"/>";
	}
	
}
