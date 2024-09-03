package com.sistema.trailers.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Genero {

	@Id
	@Column(name = "id_genero")
	private Integer id;

	private String titulo;

	public Genero(Integer id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}

	public Genero(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Genero(Integer id) {
		super();
		this.id = id;
	}

	public Genero() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
