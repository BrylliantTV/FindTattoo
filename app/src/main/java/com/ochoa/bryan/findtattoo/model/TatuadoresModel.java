package com.ochoa.bryan.findtattoo.model;

import android.net.Uri;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

/**
 * CLASE MODELO DE TATUADORES
 */
public class TatuadoresModel implements Serializable {

    String id;
    String ciudad;
    String descripcion;
    String direccion;
    String estudio;
    String instagram;
    GeoPoint localizacion;
    String nombre;
    String telefono;
    String url;
    String estilo1;
    String estilo2;
    String estilo3;
    String image1;
    String image2;
    String image3;
    String image4;

    public TatuadoresModel() {
    }

    public TatuadoresModel(String id, String ciudad, String descripcion, String direccion, String estudio, String instagram, GeoPoint localizacion, String nombre, String telefono, String url, String estilo1, String estilo2, String estilo3, String image1, String image2, String image3, String image4) {
        this.id = id;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.estudio = estudio;
        this.instagram = instagram;
        this.localizacion = localizacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.url = url;
        this.estilo1 = estilo1;
        this.estilo2 = estilo2;
        this.estilo3 = estilo3;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public GeoPoint getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(GeoPoint localizacion) {
        this.localizacion = localizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEstilo1() {
        return estilo1;
    }

    public void setEstilo1(String estilo1) {
        this.estilo1 = estilo1;
    }

    public String getEstilo2() {
        return estilo2;
    }

    public void setEstilo2(String estilo2) {
        this.estilo2 = estilo2;
    }

    public String getEstilo3() {
        return estilo3;
    }

    public void setEstilo3(String estilo3) {
        this.estilo3 = estilo3;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }
}
