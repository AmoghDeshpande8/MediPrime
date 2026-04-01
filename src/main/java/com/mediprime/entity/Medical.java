
package com.mediprime.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "medical")
public class Medical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medId;

    private String medName;
    private int medQuantity;
    private double medPrice;
    private LocalDate medExpiryDate;
    private String email;
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    // Default Constructor
    public Medical() {}

    // Getters and Setters
    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public int getMedQuantity() {
        return medQuantity;
    }

    public void setMedQuantity(int medQuantity) {
        this.medQuantity = medQuantity;
    }

    public double getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(double medPrice) {
        this.medPrice = medPrice;
    }

    public LocalDate getMedExpiryDate() {
        return medExpiryDate;
    }

    public void setMedExpiryDate(LocalDate medExpiryDate) {
        this.medExpiryDate = medExpiryDate;
    }
}
