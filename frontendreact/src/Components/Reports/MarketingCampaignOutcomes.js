import React, { useState, useEffect } from 'react';
import './MarketingCampaignOutcomes.css';

function MarketingCampaignOutcomes() {
  const [reportData, setReportData] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    fetchMarketingCampaignOutcomesReport();
  }, []);

  const fetchMarketingCampaignOutcomesReport = async () => {
    try {
      const token = localStorage.getItem('token'); // Retrieve JWT token from localStorage
      const response = await fetch(
        'http://localhost:8084/api/reports/marketing-campaign-outcomes',
        {
          headers: {
            Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
          },
        }
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.text();
      setReportData(data);
      setError('');
    } catch (error) {
      console.error('Error fetching marketing campaign outcomes report:', error);
      setError('Failed to fetch marketing campaign outcomes report: ' + error.message);
    }
  };

  // Simple function to parse the text report into a more structured format for display
  const parseReport = (reportText) => {
    const lines = reportText.trim().split('\n').filter((line) => line.includes(':'));
    const headers = ['Campaign Type', 'Count'];
    const data = lines.map((line) => {
      const parts = line.split(':').map((part) => part.trim());
      return [parts[0], parts[1]];
    });
    return { headers, data };
  };

  const { headers, data } = parseReport(reportData);

  return (
    <div className="marketing-outcomes-container">
      <h2>Marketing Campaign Outcomes</h2>
      {error && <p className="error">{error}</p>}
      {reportData && data.length > 0 ? (
        <table>
          <thead>
            <tr>
              {headers.map((header, index) => (
                <th key={index}>{header}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data.map((row, index) => (
              <tr key={index}>
                {row.map((cell, cellIndex) => (
                  <td key={cellIndex}>{cell}</td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>{reportData ? 'No marketing campaign outcomes data available.' : 'Loading marketing campaign outcomes data...'}</p>
      )}
    </div>
  );
}

export default MarketingCampaignOutcomes;