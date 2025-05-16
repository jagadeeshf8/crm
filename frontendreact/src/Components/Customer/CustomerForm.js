import React, { useState, useEffect } from "react";
import axios from "axios";
import "./CustomerForm.css";
 
const API_URL = "http://localhost:8084/customers";
 
function CustomerForm({ onFormSubmit, existingCustomer }) {
  const [formData, setFormData] = useState({
    name: "",
    contactInfo: "",
    purchaseHistory: "",
    segmentationData: "",
  });
 
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState("");
 
  useEffect(() => {
    if (existingCustomer) {
      setFormData(existingCustomer);
    } else {
      setFormData({
        name: "",
        contactInfo: "",
        purchaseHistory: "",
        segmentationData: "",
      });
    }
    setErrors({});
    setSuccessMessage("");
  }, [existingCustomer]);
 
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };
 
  const validateForm = () => {
    const newErrors = {};
    if (!formData.name) newErrors.name = "Name is required.";
    if (!formData.contactInfo) newErrors.contactInfo = "Contact Info is required.";
    if (formData.contactInfo && !/\S+@\S+\.\S+/.test(formData.contactInfo)) {
      newErrors.contactInfo = "Invalid email format.";
    }
    return newErrors;
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
 
    const newErrors = validateForm();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }
 
    try {
      const token = localStorage.getItem("token"); // Retrieve JWT token from localStorage
      const config = {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
        },
      };
 
      if (existingCustomer && existingCustomer.customerID) {
        await axios.put(`${API_URL}/${existingCustomer.customerID}`, formData, config);
        setSuccessMessage("Customer updated successfully!");
      } else {
        await axios.post(API_URL, formData, config);
        setSuccessMessage("Customer added successfully!");
      }
 
      setFormData({
        name: "",
        contactInfo: "",
        purchaseHistory: "",
        segmentationData: "",
      });
      setErrors({});
 
      setTimeout(() => {
        setSuccessMessage("");
        onFormSubmit();
      }, 2000);
    } catch (error) {
      if (error.response && error.response.data) {
        setErrors(error.response.data);
      } else {
        alert("Error: " + error.message);
      }
    }
  };
 
  return (
    <form className="form-container" onSubmit={handleSubmit}>
      {successMessage && (
        <div className="toast-success">
          {successMessage} <span className="check-icon">✔</span>
        </div>
      )}
 
      <div className="form-group">
        <label>Name:</label>
        <input
          name="name"
          value={formData.name}
          onChange={handleChange}
        />
        {errors.name && <p className="error">{errors.name}</p>}
      </div>
 
      <div className="form-group">
        <label>Contact Info (email):</label>
        <input
          name="contactInfo"
          value={formData.contactInfo}
          onChange={handleChange}
        />
        {errors.contactInfo && <p className="error">{errors.contactInfo}</p>}
      </div>
 
      <div className="form-group">
        <label>Purchase History:</label>
        <textarea
          name="purchaseHistory"
          value={formData.purchaseHistory}
          onChange={handleChange}
        />
        {errors.purchaseHistory && <p className="error">{errors.purchaseHistory}</p>}
      </div>
 
      <div className="form-group">
        <label>Segmentation Data:</label>
        <textarea
          name="segmentationData"
          value={formData.segmentationData}
          onChange={handleChange}
        />
        {errors.segmentationData && <p className="error">{errors.segmentationData}</p>}
      </div>
 
      <div className="form-buttons">
        <button type="submit" className="add-btn">
          {existingCustomer ? "Update" : "Add"} Customer
        </button>
        <button
          type="button"
          className="back-btn"
          onClick={onFormSubmit}
        >
          Back to List
        </button>
      </div>
    </form>
  );
}
 
export default CustomerForm;