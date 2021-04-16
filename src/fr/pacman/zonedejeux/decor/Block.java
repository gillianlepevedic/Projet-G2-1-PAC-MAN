package fr.pacman.zonedejeux.decor;

import java.awt.Image;

import pacman.zonedejeux.ElementGraphique;

public class Block extends ElementGraphique {
	public final static int mur = 0;
	public final static int spawn = 1;
	public final static int couloir = 2;
	public final static int superPacGomme = 3;
	public final static int pacGommeFruit = 4;
	public final static int verificationMap = -1;
	
	private int typeblock;

	public Block(float coordonnerX, float coordonnery, Image texture, boolean visible, int typeblock) {
		super(coordonnerX, coordonnery, texture, visible);
		this.typeblock = typeblock;
	}

	public int getTypeblock() {
		return typeblock;
	}

	public void setTypeblock(int typeblock) {
		this.typeblock = typeblock;
	}

}
