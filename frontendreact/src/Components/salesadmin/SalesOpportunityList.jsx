import React, { useEffect, useState } from "react";
import axios from "axios";
import SalesOpportunityForm from "./SalesOpportunityForm";
import "./SalesOpportunityList.css";

const API_URL = "http://localhost:8084/api/opportunity";

function SalesOpportunityList() {
  const [opportunities, setOpportunities] = useState([]);
  const [selectedOpportunity, setSelectedOpportunity] = useState(null);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    fetchOpportunities();
  }, []);

  const fetchOpportunities = async () => {
    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const response = await axios.get(API_URL, config);
      setOpportunities(response.data);
    } catch (error) {
      alert("Error fetching sales opportunities");
    }
  };

  const deleteOpportunity = async (id) => {
    if (window.confirm("Are you sure you want to delete this opportunity?")) {
      try {
        const token = localStorage.getItem("token");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        await axios.delete(`${API_URL}/${id}`, config);
        fetchOpportunities();
      } catch (error) {
        alert("Error deleting opportunity");
      }
    }
  };

  const handleEdit = (opportunity) => {
    setSelectedOpportunity(opportunity);
    setShowForm(true);
  };

  const handleFormSubmit = () => {
    setSelectedOpportunity(null);
    setShowForm(false);
    fetchOpportunities();
  };

  return (
    <div className="sales-opportunity-list-container">
      {!showForm ? (
        <>
          <button
            className="add-opportunity-button"
            onClick={() => setShowForm(true)}
          >
            Add Opportunity
          </button>
          <h2>Sales Opportunities</h2>
          <table className="sales-opportunity-table">
            <thead>
              <tr>
                <th>Opportunity ID</th> {/* Renamed column */}
                <th>Sales Stage</th>
                <th>Estimated Value</th>
                <th>Closing Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {opportunities.map((opportunity) => (
                <tr key={opportunity.opportunityID}>
                  <td>{opportunity.opportunityID}</td> {/* Renamed column */}
                  <td>{opportunity.salesStage}</td>
                  <td>{opportunity.estimatedValue}</td>
                  <td>{opportunity.closingDate}</td>
                  <td>
                    <button
                      className="edit-button"
                      onClick={() => handleEdit(opportunity)}
                    >
                      Edit
                    </button>
                    <button
                      className="delete-button"
                      onClick={() => deleteOpportunity(opportunity.opportunityID)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      ) : (
        <>
          <h2>{selectedOpportunity ? "Edit Opportunity" : "Add Opportunity"}</h2>
          <SalesOpportunityForm
            onFormSubmit={handleFormSubmit}
            existingOpportunity={selectedOpportunity}
          />
        </>
      )}
    </div>
  );
}

export default SalesOpportunityList;