package net.lielamar.spleef.moduels;

public class Exp {

	private float exp;
	private int total;
	private int level;
	
	public Exp(float exp, int total, int level) {
		this.setExp(exp);
		this.setTotal(total);
		this.setLevel(level);
	}

	public float getExp() {
		return exp;
	}

	public void setExp(float exp) {
		this.exp = exp;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getLevel() {
		return total;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return exp + ", " + total + ", " + level;
	}
}
