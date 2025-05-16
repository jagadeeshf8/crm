import './App.css';
import { Route, Routes, Navigate } from 'react-router-dom';
import Loginform from './Components/AdminLogin/Loginform';
import Registerform from './Components/AdminLogin/register';
import Updatepassword from './Components/AdminLogin/updatepassword';
import Mainhome from './Components/superAdmin/mainhome';
import Navbar from './Components/Reports/navbar';
import SalesPerformance from './Components/Reports/SalesPerformance';
import CustomerBehavior from './Components/Reports/CustomerBehavior';
import MarketingCampaignOutcomes from './Components/Reports/MarketingCampaignOutcomes';
import Logout from './Components/Reports/Logout';
import Home from './Components/Reports/home';
import Header from './Components/Customer/Header';
import Footer from './Components/Customer/Footer';
import ReportList from './Components/Reports/reports22/reportlist';
import CustomerList from './Components/Customer/CustomerList';
import SupportTicketList from './Components/supportadmin/SupportTicketList';
import SalesOpportunityList from './Components/salesadmin/SalesOpportunityList';
import CampaignList from './Components/campaign/CampaignList';

// Helper function to check authentication and role
const ProtectedRoute = ({ element, allowedRoles }) => {
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  if (!token) {
    return <Navigate to="/" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return element;
};

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Loginform />} />
        <Route path="/register" element={<Registerform />} />
        <Route path="/update_password" element={<Updatepassword />} />
        <Route
          path="/mainhome"
          element={
            <ProtectedRoute
              element={
                <div className="app-container">
                  <Header />
                  <Mainhome />
                  <Footer />
                </div>
              }
              allowedRoles={['SuperAdmin']}
            />
          }
        />
        <Route
          path="/home5"
          element={
            <ProtectedRoute
              element={
                <div className="app-container">
                  <Header />
                  <CampaignList />
                  <Footer />
                </div>
              }
              allowedRoles={['CampaignAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/home2"
          element={
            <ProtectedRoute
              element={
                <div className="app-container">
                  <Header />
                  <CustomerList />
                  <Footer />
                </div>
              }
              allowedRoles={['CustomerAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/home3"
          element={
            <ProtectedRoute
              element={
                <div className="app-container">
                  <Header />
                  <SupportTicketList />
                  <Footer />
                </div>
              }
              allowedRoles={['SupportAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/home4"
          element={
            <ProtectedRoute
              element={
                <div className="app-container">
                  <Header />
                  <SalesOpportunityList />
                  <Footer />
                </div>
              }
              allowedRoles={['SalesAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/home"
          element={
            <ProtectedRoute
              element={
                <>
                  <Navbar />
                  <div>
                    <Home />
                  </div>
                </>
              }
              allowedRoles={['ReportAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/reports"
          element={
            <ProtectedRoute
              element={
                <>
                  <Navbar />
                  <div className="app-container">
                    <ReportList />
                  </div>
                </>
              }
              allowedRoles={['ReportAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/sales-performance"
          element={
            <ProtectedRoute
              element={
                <>
                  <Navbar />
                  <div className="content">
                    <SalesPerformance />
                  </div>
                </>
              }
              allowedRoles={['ReportAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/customer-behavior"
          element={
            <ProtectedRoute
              element={
                <>
                  <Navbar />
                  <div className="content">
                    <CustomerBehavior />
                  </div>
                </>
              }
              allowedRoles={['ReportAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/marketing-outcomes"
          element={
            <ProtectedRoute
              element={
                <>
                  <Navbar />
                  <div className="content">
                    <MarketingCampaignOutcomes />
                  </div>
                </>
              }
              allowedRoles={['ReportAdmin', 'SuperAdmin']}
            />
          }
        />
        <Route
          path="/logout"
          element={
            <ProtectedRoute
              element={
                <>
                  <Navbar />
                  <div className="content">
                    <Logout />
                  </div>
                </>
              }
              allowedRoles={['SuperAdmin', 'ReportAdmin']}
            />
          }
        />
      </Routes>
    </div>
  );
}

export default App;
