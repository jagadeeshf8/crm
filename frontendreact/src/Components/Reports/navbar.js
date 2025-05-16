import React from 'react';
import { Link } from 'react-router-dom';
import { IoHomeOutline } from "react-icons/io5"; // Home icon
import { TbReportSearch } from "react-icons/tb"; // Reports icon
import { FcSalesPerformance } from "react-icons/fc"; // Sales Performance icon
import { FaPeopleGroup } from "react-icons/fa6"; // Customer Behavior icon
import { BiLogOut } from "react-icons/bi"; // Logout icon
import { FiSpeaker } from "react-icons/fi"; // Marketing Campaign icon
import { MdOutlineDashboard } from "react-icons/md"; // Dashboard/MainHome icon
import './navbar.css'; // Import the CSS file for styling

const Navbar = () => {
  return (
    <div className="navbar">
      {/* Favicon and Dashboard Heading */}
      <div className="navbar-header">
        <img src="/favicon.ico" alt="Favicon" className="navbar-favicon" />
        <h2 className="navbar-title">Dashboard</h2>
      </div>

      {/* Navbar Items */}
      <ul className="navbar-list">
        <li>
          <Link to="/home">
            Home
            <IoHomeOutline className="navbar-icon" />
          </Link>
        </li>
        <li>
          <Link to="/mainhome">
            Main Home
            <MdOutlineDashboard className="navbar-icon" />
          </Link>
        </li>
        <li>
          <Link to="/reports">
            Reports
            <TbReportSearch className="navbar-icon" />
          </Link>
        </li>
        <li>
          <Link to="/sales-performance">
            Sales
            <FcSalesPerformance className="navbar-icon3" />
          </Link>
        </li>
        <li>
          <Link to="/customer-behavior">
            Customer Behavior
            <FaPeopleGroup className="navbar-icon2" />
          </Link>
        </li>
        <li>
          <Link to="/marketing-outcomes">
            Marketing Campaign
            <FiSpeaker className="navbar-icon2" />
          </Link>
        </li>
        <li>
          <Link to="/logout">
            Logout
            <BiLogOut className="navbar-icon" />
          </Link>
        </li>
      </ul>
    </div>
  );
};

export default Navbar;
