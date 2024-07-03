package it.polito.tdp.exam.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;

public class Model {
	private BaseballDAO dao;
	private List<Integer> anni;
	private List<Team> teams;
	private Map<String, Team> mappa;
	private List<Team> best;
	private int max;
	private Graph<Team,DefaultWeightedEdge > grafo;
	public Model() { 
		this.dao = new BaseballDAO();
		this.anni = dao.getAnni();
		this.teams = new ArrayList<>();
		this.mappa = new HashMap<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public List<Integer> getAnni(){
		return this.anni;
		
	}
	public List<Team> getTeams(int anno){
		for (Team t: dao.readAllTeams()) {
			mappa.put(t.getTeamCode(), t);
		}
		this.teams= dao.getTeam(anno, mappa);
		return  teams;
	}
	
	public void creaGrafo(int anno) {
		Graphs.addAllVertices(this.grafo, getTeams(anno));
		Map<String, Integer> salari = dao.getSalario(anno);
		for (Team t1 : this.grafo.vertexSet()) {
			for (Team t2 : this.grafo.vertexSet()) {
				if (!t1.equals(t2)) {
					int salario = salari.get(t1.getTeamCode())+salari.get(t2.getTeamCode());
					Graphs.addEdgeWithVertices(this.grafo, t1, t2, salario);
				}
			}
		}
	}
	
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
		
	}
	
	public Map<String, Integer> getAdiacenti(Team t){
		List<Team> adiacenti = Graphs.neighborListOf(this.grafo,t);
		Map<String, Integer> result = new HashMap<>();
		for (Team t1: adiacenti) {
			DefaultWeightedEdge e = this.grafo.getEdge(t, t1);
			int peso = (int) this.grafo.getEdgeWeight(e);
			result.put(t1.getTeamCode(), peso);
		}
		return result;
	}
	
	public List<Team> trovaPercorso(Team t ){
		this.best = new ArrayList<>();
		this.max =0;
		List<Team> parziale = new ArrayList<>();
		
		parziale.add(t);
		ricorsione(parziale, t);
			
		return this.best;
	}

	private void ricorsione(List<Team> parziale, Team t) {
		Team corrente = parziale.get(parziale.size()-1);
		//condizione di uscita
		if (parziale.size()>= max) {
			this.best = new ArrayList<>(parziale);
			this.max = parziale.size();
		}
		//normale
		List<Team> successori = Graphs.neighborListOf(this.grafo, corrente);
		for (Team t1: successori){
			if (!parziale.contains(t1)) {
				if (decrescente(parziale, t1))
					parziale.add(t1);
					ricorsione (parziale, t1);
					parziale.remove(parziale.size()-1);
			}
			}
		}

	private boolean decrescente(List<Team> parziale, Team t1) {
		DefaultWeightedEdge e1 = this.grafo.getEdge(parziale.get(parziale.size()-1), t1);
		double peso1 = this.grafo.getEdgeWeight(e1);
		boolean ok = false;
		if (parziale.size()>1) {
			DefaultWeightedEdge e2 = this.grafo.getEdge(parziale.get(parziale.size()-2), parziale.get(parziale.size()-1));
			double peso2 = this.grafo.getEdgeWeight(e2);
			if (peso2<peso1) {
				ok = true;
			}
		}else {
			ok= true;
		}
		return ok;
	}

	
		
	
}
