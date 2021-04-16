package fr.pacman.zonedejeux;

import java.awt.Image;

public abstract class ElementGraphique {
	private float coordonnerX;
	private float coordonnery;
	private Image texture;
	private boolean visible;

	public ElementGraphique(float coordonnerX, float coordonnery, Image texture, boolean visible) {
		this.coordonnerX = coordonnerX;
		this.coordonnery = coordonnery;
		this.texture = texture;
		this.visible = visible;
	}

/**
 * Cette methode affiche l'images texture au coordoner coordonnerX, coordonnery.
 * si visible est a true.
 * 
 * @throws Exception si visible est a false.
 */
	public void afficher() throws Exception {

	}

	public void setCoordonnerX(float coordonnerX) {
		this.coordonnerX = coordonnerX;
	}

	public void setCoordonnery(float coordonnery) {
		this.coordonnery = coordonnery;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getCoordonnerX() {
		return coordonnerX;
	}

	public float getCoordonnery() {
		return coordonnery;
	}

	public Image getTexture() {
		return texture;
	}

	public boolean isVisible() {
		return visible;
	}
}
