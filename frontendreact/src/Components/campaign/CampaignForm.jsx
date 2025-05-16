import React, { useState, useEffect } from "react";
import axios from "axios";
import "./CampaignForm.css";
 
const API_URL = "http://localhost:8084/campaign";
 
function CampaignForm({ onFormSubmit, existingCampaign }) {
  const [formData, setFormData] = useState({
    name: "",
    type: "",
    startDate: "",
    endDate: "",
  });
 
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState("");
 
  useEffect(() => {
    if (existingCampaign) {
      setFormData(existingCampaign);
    } else {
      setFormData({
        name: "",
        type: "",
        startDate: "",
        endDate: "",
      });
    }
    setErrors({});
    setSuccessMessage("");
  }, [existingCampaign]);
 
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
 
    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
 
      if (existingCampaign && existingCampaign.campaignID) {
        await axios.put(`${API_URL}/update/${existingCampaign.campaignID}`, formData, config);
        setSuccessMessage("Campaign updated successfully!");
      } else {
        await axios.post(`${API_URL}/add`, formData, config);
        setSuccessMessage("Campaign added successfully!");
      }
 
      setFormData({
        name: "",
        type: "",
        startDate: "",
        endDate: "",
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
        <label>Campaign Name:</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
        />
        {errors.name && <p className="error">{errors.name}</p>}
      </div>
 
      <div className="form-group">
        <label>Type:</label>
        <input
          type="text"
          name="type"
          value={formData.type}
          onChange={handleChange}
        />
        {errors.type && <p className="error">{errors.type}</p>}
      </div>
 
      <div className="form-group">
        <label>Start Date:</label>
        <input
          type="date"
          name="startDate"
          value={formData.startDate}
          onChange={handleChange}
        />
        {errors.startDate && <p className="error">{errors.startDate}</p>}
      </div>
 
      <div className="form-group">
        <label>End Date:</label>
        <input
          type="date"
          name="endDate"
          value={formData.endDate}
          onChange={handleChange}
        />
        {errors.endDate && <p className="error">{errors.endDate}</p>}
      </div>
 
      <div className="form-buttons">
        <button type="submit" className="add-btn">
          {existingCampaign ? "Update" : "Add"} Campaign
        </button>
        <button type="button" className="back-btn" onClick={onFormSubmit}>
          Back to List
        </button>
      </div>
    </form>
  );
}
 
export default CampaignForm;
 