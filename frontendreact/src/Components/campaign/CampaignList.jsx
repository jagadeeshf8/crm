import React, { useEffect, useState } from "react";
import axios from "axios";
import CampaignForm from "./CampaignForm";
import "./CampaignList.css";

const API_URL = "http://localhost:8084/campaign";

function CampaignList() {
  const [campaigns, setCampaigns] = useState([]);
  const [selectedCampaign, setSelectedCampaign] = useState(null);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    fetchCampaigns();
  }, []);

  const fetchCampaigns = async () => {
    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const response = await axios.get(`${API_URL}/all`, config);
      setCampaigns(response.data);
    } catch (error) {
      alert("Error fetching campaigns");
    }
  };

  const deleteCampaign = async (id) => {
    if (window.confirm("Are you sure you want to delete this campaign?")) {
      try {
        const token = localStorage.getItem("token");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        await axios.delete(`${API_URL}/delete/${id}`, config);
        fetchCampaigns();
      } catch (error) {
        alert("Error deleting campaign");
      }
    }
  };

  const handleEdit = (campaign) => {
    setSelectedCampaign(campaign);
    setShowForm(true);
  };

  const handleFormSubmit = () => {
    setSelectedCampaign(null);
    setShowForm(false);
    fetchCampaigns();
  };

  return (
    <div className="campaign-list-container">
      {!showForm ? (
        <>
          <button
            className="add-campaign-button"
            onClick={() => setShowForm(true)}
          >
            Add Campaign
          </button>
          <h2>Campaigns</h2>
          <table className="campaign-table">
            <thead>
              <tr>
                <th>Campaign ID</th>
                <th>Campaign Name</th>
                <th>Type</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {campaigns.map((campaign) => (
                <tr key={campaign.campaignID}>
                  <td>{campaign.campaignID}</td>
                  <td>{campaign.name}</td>
                  <td>{campaign.type}</td>
                  <td>{campaign.startDate}</td>
                  <td>{campaign.endDate}</td>
                  <td>
                    <button
                      className="edit-button"
                      onClick={() => handleEdit(campaign)}
                    >
                      Edit
                    </button>
                    <button
                      className="delete-button"
                      onClick={() => deleteCampaign(campaign.campaignID)}
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
          <h2>{selectedCampaign ? "Edit Campaign" : "Add Campaign"}</h2>
          <CampaignForm
            onFormSubmit={handleFormSubmit}
            existingCampaign={selectedCampaign}
          />
        </>
      )}
    </div>
  );
}

export default CampaignList;