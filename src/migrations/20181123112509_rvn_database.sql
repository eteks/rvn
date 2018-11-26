
--
-- Table structure for table `acbversion`
--

CREATE TABLE `acbversion` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ACB version',
  `acb_versionname` float(10,1) NOT NULL COMMENT 'Uniquely stores the ACB version name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the ACB version',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the ACB version',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified the ACB version recently',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the ACB version whether it should be active or not',
  `flag` tinyint(1) NOT NULL COMMENT 'Stores the flag status of the current version to identify whether it is temporarily saved or permanently submitted. If it is 0, we can update it or else 1 we can''t able to update the permanent one',
  `subversion_of` int(11) DEFAULT NULL,
  `features_fully_touchedstatus` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Stores the status of wether all the features has been touched or not in this version'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `acbversion_group`
--

CREATE TABLE `acbversion_group` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ACB version group  ',
  `acbversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ACB version',
  `ivnversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ivnversion',
  `pdbversion_id` int(11) NOT NULL COMMENT 'Stores mapped id of the pdbversion',
  `vehicleversion_id` int(11) NOT NULL COMMENT 'Stores mapped id of the vehicleversion',
  `vehicle_id` int(11) NOT NULL COMMENT 'Stores mapped id of the vehicle',
  `domain_and_features_mapping_id` int(11) DEFAULT NULL COMMENT 'Stores the mapping id of the features with domain',
  `ecu_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ECU',
  `inputsignal_group` varchar(200) NOT NULL COMMENT 'Stores the mapped id''s of the input signal with comma seperated. E.g: 1,2,3',
  `outputsignal_group` varchar(200) NOT NULL COMMENT 'Stores the mapped id''s of the output signal with comma seperated. E.g: 1,2,3',
  `touchedstatus` tinyint(1) NOT NULL COMMENT 'Stores the touched status of the ACB version group. If 1, it is touched and 0, it is not touched'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `acb_inputsignal`
--

CREATE TABLE `acb_inputsignal` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ACB input signal',
  `input_signal_id` int(11) NOT NULL COMMENT 'Stores the signal id for input',
  `input_network_id` int(11) NOT NULL COMMENT 'Stores the network or hardware id of the input signal. If the input signal is not having network or hardware, NULL will be stored',
  `network_type` varchar(25) NOT NULL COMMENT 'Stores the network type',
  `pdbversion_group_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the pdbversion group to identify the vehicle, model and features'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `acb_outputsignal`
--

CREATE TABLE `acb_outputsignal` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ACB output signal',
  `output_signal_id` int(11) NOT NULL COMMENT 'Stores the signal id for output',
  `output_network_id` int(11) NOT NULL COMMENT 'Stores the network or hardware id of the output signal. If the output signal is not having network or hardware, NULL will be stored',
  `network_type` varchar(25) NOT NULL COMMENT 'Stores the network type',
  `pdbversion_group_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the pdbversion group to identify the vehicle, model and features'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `domain`
--

CREATE TABLE `domain` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the domain',
  `domain_name` longtext NOT NULL COMMENT 'Uniquely stores the domain name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Stores the modified date of the domain',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the domain',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who user created or modified the domain',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'stores the status of the domain whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `domain_and_features_mapping`
--

CREATE TABLE `domain_and_features_mapping` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the mapped domain and features',
  `domain_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the domain',
  `feature_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the features',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the mapped domain and features'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ecu_and_variants_mapping`
--

CREATE TABLE `ecu_and_variants_mapping` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the mapped ECU and Variants',
  `ecu_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the ECU',
  `variant_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the variants',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the mapped domain and features'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email_verify`
--

CREATE TABLE `email_verify` (
  `user_id` int(11) NOT NULL,
  `verification_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `engine_control_unit`
--

CREATE TABLE `engine_control_unit` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ECU',
  `ecu_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the ECU name',
  `ecu_description` text NOT NULL COMMENT 'Stores the description of the ECU''s',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the ECU''s',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created modified the ECU',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created this ECU',
  `status` tinyint(1) DEFAULT '1' COMMENT 'Stores the status of the ECU whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `features`
--

CREATE TABLE `features` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the features',
  `feature_name` text NOT NULL COMMENT 'Uniquely stores the feature name',
  `feature_description` text NOT NULL COMMENT 'Stores the description of the feature',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Stores latest modified date of the features',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the features',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified this features',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of the features whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the user_groups',
  `group_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the group name',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the user groups whether it should be active or not',
  `route_pages` varchar(200) DEFAULT NULL COMMENT 'Stores the route pages url for this particular group',
  `is_superadmin` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Stores the boolean value whether this group is super admin or not',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores latest modified date of the user groups',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created dated of the user groups'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `group_name`, `status`, `route_pages`, `is_superadmin`, `modified_date`, `created_date`) VALUES
(1, 'IVN Supervisor', 1, 'ivn_supervisor', 0, '2018-09-22 10:32:42', '2018-06-10 18:30:00'),
(2, 'Admin', 1, 'admin', 1, '2018-09-25 06:47:13', '2018-06-10 18:30:00'),
(3, 'PDB Owner', 1, 'pdb_owner', 0, '2018-09-09 18:44:11', '2018-06-10 18:30:00'),
(4, 'IVN Engineer', 1, 'ivn_engineer', 0, '2018-09-09 18:44:16', '2018-06-10 18:30:00'),
(5, 'ACB Owner', 1, 'acb', 0, '2018-09-09 18:44:21', '2018-06-10 18:30:00'),
(6, 'System Owner', 1, 'sys_engg', 0, '2018-09-19 07:29:15', '2018-06-10 18:30:00'),
(7, 'Safety Owner ', 1, 'dashboard', 0, '2018-09-09 18:59:28', '2018-06-10 18:30:00'),
(8, 'Legislation Owner', 1, 'dashboard', 0, '2018-09-09 18:59:30', '2018-06-10 18:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `group_permissions`
--

CREATE TABLE `group_permissions` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the group permissions',
  `group_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the group',
  `module_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the module',
  `modulepermission_id` int(11) NOT NULL COMMENT 'Stores the operation id of the module permission 1-Add, 2-Edit,3 -View, 4- Delete'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ivnversion`
--

CREATE TABLE `ivnversion` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the IVN version',
  `ivn_versionname` float(10,1) NOT NULL COMMENT 'Uniquely stores the IVN version name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the IVN version',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the IVN version',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified the IVN version recently',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the IVN version whether it should be active or not',
  `flag` tinyint(1) NOT NULL COMMENT 'Stores the flag status of the current version to identify whether it is temporarily saved or permanently submitted. If it is 0, we can update it or else 1 we can''t able to update the permanent one'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ivnversion_group`
--

CREATE TABLE `ivnversion_group` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the IVN version group',
  `ivnversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the IVN version',
  `canmodel_group` text NOT NULL COMMENT 'Stores the CAN models id with comma seperator (E.g: 1,2,3)',
  `linmodel_group` text NOT NULL COMMENT 'Stores the LIN models id with comma seperator (E.g: 1,2,3)',
  `hardwaremodel_group` text NOT NULL COMMENT 'Stores the H/W models id with comma seperator (E.g: 1,2,3)',
  `signal_group` text NOT NULL COMMENT 'Stores the signal id''s with comma seperator (E.g: 1,2,3)',
  `ecu_group` text NOT NULL COMMENT 'Stores the ECU id''s with comma seperator (E.g: 1,2,3)'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ivn_canmodels`
--

CREATE TABLE `ivn_canmodels` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the applied CAN models',
  `ivnversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ivnversion',
  `network_can_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the CAN',
  `vehicle_and_model_mapping_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle model',
  `available_status` tinyint(1) NOT NULL COMMENT 'Stores the available status of the particular can and model integration E.g 0 or 1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ivn_hardwaremodels`
--

CREATE TABLE `ivn_hardwaremodels` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the applied Hardware models',
  `ivnversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ivnversion',
  `network_hardware_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the Hardware',
  `vehicle_and_model_mapping_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle model',
  `available_status` tinyint(1) NOT NULL COMMENT 'Stores the available status of the particular Hardware and model integration E.g 0 or 1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ivn_linmodels`
--

CREATE TABLE `ivn_linmodels` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the applied LIN models',
  `ivnversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ivnversion',
  `network_lin_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the LIN',
  `vehicle_and_model_mapping_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle model',
  `available_status` tinyint(1) NOT NULL COMMENT 'Stores the available status of the particular LIN and model integration(y,n,o)'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `modelversion`
--

CREATE TABLE `modelversion` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ACB version',
  `model_versionname` float(10,1) NOT NULL COMMENT 'Uniquely stores the Model version name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the Model version',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the Model version',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified the Model version recently',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the Model version whether it should be active or not',
  `flag` tinyint(1) NOT NULL COMMENT 'Stores the flag status of the current version to identify whether it is temporarily saved or permanently submitted. If it is 0, we can update it or else 1 we can''t able to update the permanent one'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `modelversion_group`
--

CREATE TABLE `modelversion_group` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the Model version group',
  `modelversion_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the modelversion',
  `vehicleversion_id` int(11) DEFAULT NULL COMMENT 'Stores the mapped id of the vehicleversion',
  `vehicle_id` int(11) DEFAULT NULL COMMENT 'Stores the mapped id of the vehicle',
  `acbversion_id` int(11) DEFAULT NULL COMMENT 'Stores the mapped id of the ACB version',
  `vehicle_and_model_mapping_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle and model mapping',
  `ecu_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ECU',
  `variant_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the variants'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the module',
  `modulename` varchar(200) NOT NULL COMMENT 'Uniquely stores the module name',
  `icon_code` varchar(200) NOT NULL COMMENT 'stores the html code of icon E.g:<i class="fa fa-vehicle"></i>',
  `route_pages` varchar(100) NOT NULL COMMENT 'Stores the route url of the pages to redirect',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the module whether it should be active or not',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores latest modified date of the module',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores created date of the module'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `module_permission`
--

CREATE TABLE `module_permission` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the module permission',
  `module_id` int(11) NOT NULL COMMENT 'Stores the mapped module id',
  `operation_id` int(10) NOT NULL COMMENT 'Stores the operation id to be assigned for the below module id E.g: 1-Add 2-Edit 3-View 4-Delete'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `network`
--

CREATE TABLE `network` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the can network',
  `network_name` text NOT NULL COMMENT 'Uniquely stores the network name',
  `network_description` text NOT NULL COMMENT 'Stores the description of the network',
  `network_type` varchar(100) NOT NULL COMMENT 'Stores the network type',
  `modified_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the CAN network',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the CAN network',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified this CAN network',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of this CAN network whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `network_can`
--

CREATE TABLE `network_can` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the can network',
  `can_network_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the CAN network name',
  `can_network_description` text NOT NULL COMMENT 'Stores the description of the CAN network',
  `modified_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the CAN network',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the CAN network',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified this CAN network',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of this CAN network whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `network_hardware`
--

CREATE TABLE `network_hardware` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the H/W network',
  `hardware_network_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the H/W network name',
  `hardware_network_description` text NOT NULL COMMENT 'Stores the description of the Hardware network',
  `modified_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the H/W network',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the H/W network',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified this H/W network',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of this H/W network whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `network_lin`
--

CREATE TABLE `network_lin` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the LIN network',
  `lin_network_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the LIN network name',
  `lin_network_description` text NOT NULL COMMENT 'Stores the description of the LIN network',
  `modified_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the LIN network',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the LIN network',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified this LIN network',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of this LIN network whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique of the Notification',
  `sender_id` int(11) NOT NULL COMMENT 'Stores the Sender''s User Id',
  `receiver_id` varchar(20) NOT NULL COMMENT 'Stores the Group Id',
  `version_type_id` int(11) NOT NULL COMMENT 'Stores the version type Id',
  `version_id` float(10,1) NOT NULL COMMENT 'Strores the Version Name',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the Notification'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `numbers`
--

CREATE TABLE `numbers` (
  `n` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pdbversion`
--

CREATE TABLE `pdbversion` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the pdb version',
  `pdb_versionname` float(10,1) NOT NULL COMMENT 'Uniquely stores the pdb version name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Stores the modified date of the pdb version',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the pdb version',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who user created or modified the pdb version',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of the pdb version',
  `flag` tinyint(1) NOT NULL COMMENT 'Stores the flag status of the current version to identify whether it is temporarily saved or permanently submitted. If it is 0, we can update it or else 1 we can''t able to update the permanent one'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pdbversion_group`
--

CREATE TABLE `pdbversion_group` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of pdbversion group',
  `pdbversion_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the pdb version',
  `vehicle_and_model_mapping_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the vehicle and model',
  `domain_and_features_mapping_id` int(11) NOT NULL COMMENT 'Stores the mapping id of the domain and features',
  `available_status` varchar(20) NOT NULL COMMENT 'Stores the available status value of the particular domain and features(yes, no, optional)'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `signals`
--

CREATE TABLE `signals` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the signal',
  `signal_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the signal name',
  `signal_alias` varchar(100) DEFAULT NULL COMMENT 'Uniquely stores the signal alias name',
  `signal_description` text NOT NULL COMMENT 'Stores the description of the signal',
  `signal_length` int(11) DEFAULT NULL COMMENT 'Stores the length value of the signal',
  `signal_byteorder` varchar(50) DEFAULT NULL COMMENT 'Stores the byteorder of the signal',
  `signal_unit` varchar(50) DEFAULT NULL COMMENT 'Stores the unit value of the signal',
  `signal_valuetype` varchar(50) DEFAULT NULL COMMENT 'Stores the value type of the signal',
  `signal_initvalue` int(11) DEFAULT NULL COMMENT 'Stores the init value of the signal',
  `signal_factor` double(22,11) DEFAULT NULL COMMENT 'Stores the factor value of the signal',
  `signal_offset` int(11) DEFAULT NULL COMMENT 'Stores the offset value of the signal',
  `signal_minimum` int(11) DEFAULT NULL COMMENT 'Stores the minimum value of the signal',
  `signal_maximum` int(11) DEFAULT NULL COMMENT 'Stores the maximum value of the signal',
  `signal_valuetable` varchar(200) DEFAULT NULL COMMENT 'Stores the value table of the signal',
  `can_id_group` text COMMENT 'Stores the selected CAN network id''s with comma seperator(E.g:1,2,3)',
  `lin_id_group` text COMMENT 'Stores the selected LIN network id''s with comma seperator(E.g:1,2,3)',
  `hw_id_group` text COMMENT 'Stores the selected HW network id''s with comma seperator(E.g:1,2,3)',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the signal ',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the signal',
  `created_or_updated_by` int(11) NOT NULL COMMENT 'Stores the user id who created this signal',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of the signal whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `status_notification`
--

CREATE TABLE `status_notification` (
  `receiver_id` int(11) NOT NULL COMMENT 'Stores Receiver''s User Id',
  `notification_id` int(11) NOT NULL COMMENT 'Stores Notification Id'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `systemversion`
--

CREATE TABLE `systemversion` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the System version',
  `system_versionname` float(10,1) NOT NULL COMMENT 'Uniquely stores the System version name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the latest modified date of the System version',
  `created_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the System version',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified the System version recently',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the System version whether it should be active or not',
  `flag` tinyint(1) NOT NULL COMMENT 'Stores the flag status of the current version to identify whether it is temporarily saved or permanently submitted. If it is 0, we can update it or else 1 we can''t able to update the permanent one'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `systemversion_group`
--

CREATE TABLE `systemversion_group` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the ACB version group  ',
  `systemversion_id` int(11) NOT NULL,
  `vehicleversion_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `acbversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ACB version',
  `domain_and_features_mapping_id` int(11) DEFAULT NULL COMMENT 'Stores the mapping id of the features with domain',
  `ecu_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the ECU',
  `variant_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the Variant',
  `available_status` varchar(20) NOT NULL COMMENT 'Stores the available status value of the particular domain and features(yes, no, optional)'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique of the user',
  `username` varchar(50) NOT NULL COMMENT 'Stores the username of the user. It should be unique',
  `employee_id` varchar(50) NOT NULL COMMENT 'Stores the employee Id of the user. It should be unique',
  `firstname` varchar(50) NOT NULL COMMENT 'Stores the firstname of the user',
  `lastname` varchar(50) NOT NULL COMMENT 'Stores the lastname of the user',
  `password` varchar(50) NOT NULL COMMENT 'Stores the password of the user',
  `email` varchar(50) NOT NULL COMMENT 'Stores the email of the user. It should be unique',
  `supervisor_email` varchar(50) NOT NULL COMMENT 'Stores the supervisor email of the user',
  `mobile_number` bigint(20) NOT NULL COMMENT 'Stores the mobile number of the user',
  `group_id` int(11) NOT NULL COMMENT 'Stores the mapped group id of the user',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the user whether it should be active or not',
  `email_status` tinyint(1) NOT NULL COMMENT 'Stores the email verification Status',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores latest modified date of the user',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the user'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `employee_id`, `firstname`, `lastname`, `password`, `email`, `supervisor_email`, `mobile_number`, `group_id`, `status`, `email_status`, `modified_date`, `created_date`) VALUES
(1, 'admin', '300', 'Lokendar', 'KA', 'admin', 'bohadiz@jbnote.com', 'lokendar@gmail.com', 123456, 2, 1, 0, '2018-10-23 07:06:52', '2018-06-10 18:30:00'),
(2, 'mrv_ivn_1', '1', 'mrv_ivn_1', 'mrv_ivn_1', 'ivn@123', 'yivipog@jbnote.com', 'mrvivn@gmail.com', 9876543210, 3, 1, 0, '2018-10-23 07:06:41', '2018-06-10 18:30:00'),
(6, 'venkat', '105', 'Venkatesh', 'S', 'qwerty', 'xatevi@jbnote.com', 'test@gmail.com', 9517536458, 2, 1, 0, '2018-10-23 06:45:14', '2018-10-22 09:34:42'),
(7, 'user', '478', 'User', 'user', '12345', 'user@jbnote.com', 'sqwqe@gmail.com', 789321458, 1, 1, 1, '2018-10-23 09:35:18', '2018-10-23 07:36:03');

-- --------------------------------------------------------

--
-- Table structure for table `variants`
--

CREATE TABLE `variants` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the variants',
  `variant_name` varchar(100) NOT NULL COMMENT 'Uniquely stores the variant name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Stores the modified date of the variant',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the variant',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who user created or modified the variant',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'stores the status of the variant whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the vehicle',
  `vehiclename` varchar(100) NOT NULL COMMENT 'Uniquely stores the vehicle name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores the modified date of the vehicle',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the vehicle',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who user created or modified the vehicle',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of the vehicle whether it should be active or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vehiclemodel`
--

CREATE TABLE `vehiclemodel` (
  `id` int(11) NOT NULL COMMENT 'Stores the Unique id of vehicle model',
  `modelname` varchar(100) NOT NULL COMMENT 'Uniquely stores the model name',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores latest modified date of the model',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the vehicle model',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or updated this model',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Stores the status of the vehicle whether it  should be active or not, By default it is active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vehicleversion`
--

CREATE TABLE `vehicleversion` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the vehicle version',
  `versionname` float(10,1) NOT NULL COMMENT 'Uniquely stores the version name',
  `status` tinyint(1) NOT NULL COMMENT 'Stores the status of the version whether it should be active or not',
  `flag` tinyint(1) NOT NULL COMMENT 'Stores the flag status of the current version to identify whether it is temporarily saved or permanently submitted. If it is 0, we can update it or else 1 we can''t able to update the permanent one',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Stores latest modified date of the vehicle version',
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Stores the created date of the vehicle version',
  `created_or_updated_by` int(11) DEFAULT NULL COMMENT 'Stores the id of the user who created or modified this vehicle version'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_and_model_mapping`
--

CREATE TABLE `vehicle_and_model_mapping` (
  `id` int(11) NOT NULL COMMENT 'Stores the unique id of the vehicle and model mapping',
  `vehicleversion_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle version',
  `vehicle_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle',
  `model_id` int(11) NOT NULL COMMENT 'Stores the mapped id of the vehicle model'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acbversion`
--
ALTER TABLE `acbversion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`),
  ADD KEY `subversion_of` (`subversion_of`);

--
-- Indexes for table `acbversion_group`
--
ALTER TABLE `acbversion_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `acbversion_id` (`acbversion_id`),
  ADD KEY `ivnversion_id` (`ivnversion_id`),
  ADD KEY `ecu_id` (`ecu_id`),
  ADD KEY `pdbversion_id` (`pdbversion_id`),
  ADD KEY `vehicleversion_id` (`vehicleversion_id`),
  ADD KEY `vehicle_id` (`vehicle_id`),
  ADD KEY `domain_and_features_mapping_id` (`domain_and_features_mapping_id`);

--
-- Indexes for table `acb_inputsignal`
--
ALTER TABLE `acb_inputsignal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pdbversion_group_id` (`pdbversion_group_id`),
  ADD KEY `input_signal_id` (`input_signal_id`);

--
-- Indexes for table `acb_outputsignal`
--
ALTER TABLE `acb_outputsignal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `output_signal_id` (`output_signal_id`),
  ADD KEY `pdbversion_group_id` (`pdbversion_group_id`);

--
-- Indexes for table `domain`
--
ALTER TABLE `domain`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `domain_and_features_mapping`
--
ALTER TABLE `domain_and_features_mapping`
  ADD PRIMARY KEY (`id`),
  ADD KEY `domain_id` (`domain_id`),
  ADD KEY `feature_id` (`feature_id`);

--
-- Indexes for table `ecu_and_variants_mapping`
--
ALTER TABLE `ecu_and_variants_mapping`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ecu_id` (`ecu_id`),
  ADD KEY `variant_id` (`variant_id`);

--
-- Indexes for table `email_verify`
--
ALTER TABLE `email_verify`
  ADD KEY `fk_userId` (`user_id`);

--
-- Indexes for table `engine_control_unit`
--
ALTER TABLE `engine_control_unit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `features`
--
ALTER TABLE `features`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `group_permissions`
--
ALTER TABLE `group_permissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`),
  ADD KEY `module_id` (`module_id`),
  ADD KEY `modulepermission_id` (`modulepermission_id`);

--
-- Indexes for table `ivnversion`
--
ALTER TABLE `ivnversion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `ivnversion_group`
--
ALTER TABLE `ivnversion_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ivnversion_id` (`ivnversion_id`);

--
-- Indexes for table `ivn_canmodels`
--
ALTER TABLE `ivn_canmodels`
  ADD PRIMARY KEY (`id`),
  ADD KEY `network_can_id` (`network_can_id`),
  ADD KEY `ivnversion_id` (`ivnversion_id`),
  ADD KEY `vehicle_and_model_mapping_id` (`vehicle_and_model_mapping_id`);

--
-- Indexes for table `ivn_hardwaremodels`
--
ALTER TABLE `ivn_hardwaremodels`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ivnversion_id` (`ivnversion_id`),
  ADD KEY `network_hardware_id` (`network_hardware_id`),
  ADD KEY `vehicle_and_model_mapping_id` (`vehicle_and_model_mapping_id`);

--
-- Indexes for table `ivn_linmodels`
--
ALTER TABLE `ivn_linmodels`
  ADD PRIMARY KEY (`id`),
  ADD KEY `network_lin_id` (`network_lin_id`),
  ADD KEY `ivnversion_id` (`ivnversion_id`),
  ADD KEY `vehicle_and_model_mapping_id` (`vehicle_and_model_mapping_id`);

--
-- Indexes for table `modelversion`
--
ALTER TABLE `modelversion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `modelversion_group`
--
ALTER TABLE `modelversion_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `modelversion_id` (`modelversion_id`),
  ADD KEY `vehicle_and_model_mapping_id` (`vehicle_and_model_mapping_id`),
  ADD KEY `ecu_id` (`ecu_id`),
  ADD KEY `variant_id` (`variant_id`),
  ADD KEY `vehicleversion_id` (`vehicleversion_id`),
  ADD KEY `vehicle_id` (`vehicle_id`),
  ADD KEY `acbversion_id` (`acbversion_id`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `module_permission`
--
ALTER TABLE `module_permission`
  ADD PRIMARY KEY (`id`),
  ADD KEY `module_id` (`module_id`);

--
-- Indexes for table `network`
--
ALTER TABLE `network`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `network_can`
--
ALTER TABLE `network_can`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `network_hardware`
--
ALTER TABLE `network_hardware`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `network_lin`
--
ALTER TABLE `network_lin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender_id_fk` (`sender_id`);

--
-- Indexes for table `numbers`
--
ALTER TABLE `numbers`
  ADD UNIQUE KEY `n` (`n`);

--
-- Indexes for table `pdbversion`
--
ALTER TABLE `pdbversion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `pdbversion_group`
--
ALTER TABLE `pdbversion_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pdbversion_id` (`pdbversion_id`,`vehicle_and_model_mapping_id`,`domain_and_features_mapping_id`),
  ADD KEY `pdbversion_id_2` (`pdbversion_id`),
  ADD KEY `vehicle_and_model_mapping_id` (`vehicle_and_model_mapping_id`),
  ADD KEY `domain_and_features_mapping_id` (`domain_and_features_mapping_id`);

--
-- Indexes for table `signals`
--
ALTER TABLE `signals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `status_notification`
--
ALTER TABLE `status_notification`
  ADD KEY `receiver_id_fk` (`receiver_id`),
  ADD KEY `notification_id_fk` (`notification_id`);

--
-- Indexes for table `systemversion`
--
ALTER TABLE `systemversion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `systemversion_group`
--
ALTER TABLE `systemversion_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vehicleversion_id` (`vehicleversion_id`),
  ADD KEY `vehicle_id` (`vehicle_id`),
  ADD KEY `domain_and_features_mapping_id` (`domain_and_features_mapping_id`),
  ADD KEY `acbversion_id` (`acbversion_id`),
  ADD KEY `systemversion_id` (`systemversion_id`),
  ADD KEY `variant_id` (`variant_id`),
  ADD KEY `ecu_id` (`ecu_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`);

--
-- Indexes for table `variants`
--
ALTER TABLE `variants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vehiclemodel`
--
ALTER TABLE `vehiclemodel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`),
  ADD KEY `created_or_updated_by_2` (`created_or_updated_by`);

--
-- Indexes for table `vehicleversion`
--
ALTER TABLE `vehicleversion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_or_updated_by` (`created_or_updated_by`);

--
-- Indexes for table `vehicle_and_model_mapping`
--
ALTER TABLE `vehicle_and_model_mapping`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vehicleversion_id` (`vehicleversion_id`),
  ADD KEY `vehicle_id` (`vehicle_id`),
  ADD KEY `model_id` (`model_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acbversion`
--
ALTER TABLE `acbversion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ACB version';
--
-- AUTO_INCREMENT for table `acbversion_group`
--
ALTER TABLE `acbversion_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ACB version group  ';
--
-- AUTO_INCREMENT for table `acb_inputsignal`
--
ALTER TABLE `acb_inputsignal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ACB input signal';
--
-- AUTO_INCREMENT for table `acb_outputsignal`
--
ALTER TABLE `acb_outputsignal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ACB output signal';
--
-- AUTO_INCREMENT for table `domain`
--
ALTER TABLE `domain`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the domain';
--
-- AUTO_INCREMENT for table `domain_and_features_mapping`
--
ALTER TABLE `domain_and_features_mapping`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the mapped domain and features';
--
-- AUTO_INCREMENT for table `ecu_and_variants_mapping`
--
ALTER TABLE `ecu_and_variants_mapping`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the mapped ECU and Variants';
--
-- AUTO_INCREMENT for table `engine_control_unit`
--
ALTER TABLE `engine_control_unit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ECU';
--
-- AUTO_INCREMENT for table `features`
--
ALTER TABLE `features`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the features';
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the user_groups', AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `group_permissions`
--
ALTER TABLE `group_permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the group permissions';
--
-- AUTO_INCREMENT for table `ivnversion`
--
ALTER TABLE `ivnversion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the IVN version';
--
-- AUTO_INCREMENT for table `ivnversion_group`
--
ALTER TABLE `ivnversion_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the IVN version group';
--
-- AUTO_INCREMENT for table `ivn_canmodels`
--
ALTER TABLE `ivn_canmodels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the applied CAN models';
--
-- AUTO_INCREMENT for table `ivn_hardwaremodels`
--
ALTER TABLE `ivn_hardwaremodels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the applied Hardware models';
--
-- AUTO_INCREMENT for table `ivn_linmodels`
--
ALTER TABLE `ivn_linmodels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the applied LIN models';
--
-- AUTO_INCREMENT for table `modelversion`
--
ALTER TABLE `modelversion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ACB version';
--
-- AUTO_INCREMENT for table `modelversion_group`
--
ALTER TABLE `modelversion_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the Model version group';
--
-- AUTO_INCREMENT for table `module`
--
ALTER TABLE `module`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the module';
--
-- AUTO_INCREMENT for table `module_permission`
--
ALTER TABLE `module_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the module permission';
--
-- AUTO_INCREMENT for table `network`
--
ALTER TABLE `network`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the can network';
--
-- AUTO_INCREMENT for table `network_can`
--
ALTER TABLE `network_can`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the can network';
--
-- AUTO_INCREMENT for table `network_hardware`
--
ALTER TABLE `network_hardware`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the H/W network';
--
-- AUTO_INCREMENT for table `network_lin`
--
ALTER TABLE `network_lin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the LIN network';
--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique of the Notification';
--
-- AUTO_INCREMENT for table `pdbversion`
--
ALTER TABLE `pdbversion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the pdb version';
--
-- AUTO_INCREMENT for table `pdbversion_group`
--
ALTER TABLE `pdbversion_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of pdbversion group';
--
-- AUTO_INCREMENT for table `signals`
--
ALTER TABLE `signals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the signal';
--
-- AUTO_INCREMENT for table `systemversion`
--
ALTER TABLE `systemversion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the System version';
--
-- AUTO_INCREMENT for table `systemversion_group`
--
ALTER TABLE `systemversion_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the ACB version group  ';
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique of the user', AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `variants`
--
ALTER TABLE `variants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the variants';
--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the vehicle';
--
-- AUTO_INCREMENT for table `vehiclemodel`
--
ALTER TABLE `vehiclemodel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the Unique id of vehicle model';
--
-- AUTO_INCREMENT for table `vehicleversion`
--
ALTER TABLE `vehicleversion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the vehicle version';
--
-- AUTO_INCREMENT for table `vehicle_and_model_mapping`
--
ALTER TABLE `vehicle_and_model_mapping`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Stores the unique id of the vehicle and model mapping';
--
-- Constraints for dumped tables
--

--
-- Constraints for table `acbversion`
--
ALTER TABLE `acbversion`
  ADD CONSTRAINT `acbversion_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_ibfk_2` FOREIGN KEY (`subversion_of`) REFERENCES `acbversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `acbversion_group`
--
ALTER TABLE `acbversion_group`
  ADD CONSTRAINT `acbversion_group_ibfk_1` FOREIGN KEY (`acbversion_id`) REFERENCES `acbversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_group_ibfk_2` FOREIGN KEY (`ivnversion_id`) REFERENCES `ivnversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_group_ibfk_3` FOREIGN KEY (`ecu_id`) REFERENCES `engine_control_unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_group_ibfk_4` FOREIGN KEY (`pdbversion_id`) REFERENCES `pdbversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_group_ibfk_5` FOREIGN KEY (`vehicleversion_id`) REFERENCES `vehicleversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_group_ibfk_6` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acbversion_group_ibfk_7` FOREIGN KEY (`domain_and_features_mapping_id`) REFERENCES `domain_and_features_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `acb_inputsignal`
--
ALTER TABLE `acb_inputsignal`
  ADD CONSTRAINT `acb_inputsignal_ibfk_1` FOREIGN KEY (`input_signal_id`) REFERENCES `signals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acb_inputsignal_ibfk_2` FOREIGN KEY (`pdbversion_group_id`) REFERENCES `pdbversion_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `acb_outputsignal`
--
ALTER TABLE `acb_outputsignal`
  ADD CONSTRAINT `acb_outputsignal_ibfk_1` FOREIGN KEY (`output_signal_id`) REFERENCES `signals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `acb_outputsignal_ibfk_2` FOREIGN KEY (`pdbversion_group_id`) REFERENCES `pdbversion_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `domain`
--
ALTER TABLE `domain`
  ADD CONSTRAINT `domain_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `domain_and_features_mapping`
--
ALTER TABLE `domain_and_features_mapping`
  ADD CONSTRAINT `domain_and_features_mapping_ibfk_1` FOREIGN KEY (`domain_id`) REFERENCES `domain` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `domain_and_features_mapping_ibfk_2` FOREIGN KEY (`feature_id`) REFERENCES `features` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ecu_and_variants_mapping`
--
ALTER TABLE `ecu_and_variants_mapping`
  ADD CONSTRAINT `ecu_and_variants_mapping_ibfk_1` FOREIGN KEY (`ecu_id`) REFERENCES `engine_control_unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ecu_and_variants_mapping_ibfk_2` FOREIGN KEY (`variant_id`) REFERENCES `variants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `email_verify`
--
ALTER TABLE `email_verify`
  ADD CONSTRAINT `fk_userId` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `engine_control_unit`
--
ALTER TABLE `engine_control_unit`
  ADD CONSTRAINT `engine_control_unit_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `features`
--
ALTER TABLE `features`
  ADD CONSTRAINT `features_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `group_permissions`
--
ALTER TABLE `group_permissions`
  ADD CONSTRAINT `group_permissions_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_permissions_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ivnversion`
--
ALTER TABLE `ivnversion`
  ADD CONSTRAINT `ivnversion_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ivnversion_group`
--
ALTER TABLE `ivnversion_group`
  ADD CONSTRAINT `ivnversion_group_ibfk_1` FOREIGN KEY (`ivnversion_id`) REFERENCES `ivnversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ivn_canmodels`
--
ALTER TABLE `ivn_canmodels`
  ADD CONSTRAINT `ivn_canmodels_ibfk_1` FOREIGN KEY (`network_can_id`) REFERENCES `network` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ivn_canmodels_ibfk_3` FOREIGN KEY (`ivnversion_id`) REFERENCES `ivnversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ivn_canmodels_ibfk_4` FOREIGN KEY (`vehicle_and_model_mapping_id`) REFERENCES `vehicle_and_model_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ivn_hardwaremodels`
--
ALTER TABLE `ivn_hardwaremodels`
  ADD CONSTRAINT `ivn_hardwaremodels_ibfk_1` FOREIGN KEY (`ivnversion_id`) REFERENCES `ivnversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ivn_hardwaremodels_ibfk_2` FOREIGN KEY (`network_hardware_id`) REFERENCES `network` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ivn_hardwaremodels_ibfk_3` FOREIGN KEY (`vehicle_and_model_mapping_id`) REFERENCES `vehicle_and_model_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ivn_linmodels`
--
ALTER TABLE `ivn_linmodels`
  ADD CONSTRAINT `ivn_linmodels_ibfk_1` FOREIGN KEY (`network_lin_id`) REFERENCES `network` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ivn_linmodels_ibfk_3` FOREIGN KEY (`ivnversion_id`) REFERENCES `ivnversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ivn_linmodels_ibfk_4` FOREIGN KEY (`vehicle_and_model_mapping_id`) REFERENCES `vehicle_and_model_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `modelversion_group`
--
ALTER TABLE `modelversion_group`
  ADD CONSTRAINT `modelversion_group_ibfk_1` FOREIGN KEY (`modelversion_id`) REFERENCES `modelversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modelversion_group_ibfk_2` FOREIGN KEY (`vehicle_and_model_mapping_id`) REFERENCES `vehicle_and_model_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modelversion_group_ibfk_3` FOREIGN KEY (`ecu_id`) REFERENCES `engine_control_unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modelversion_group_ibfk_4` FOREIGN KEY (`variant_id`) REFERENCES `variants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modelversion_group_ibfk_5` FOREIGN KEY (`vehicleversion_id`) REFERENCES `vehicleversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modelversion_group_ibfk_6` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `modelversion_group_ibfk_7` FOREIGN KEY (`acbversion_id`) REFERENCES `acbversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `module_permission`
--
ALTER TABLE `module_permission`
  ADD CONSTRAINT `module_permission_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `network_can`
--
ALTER TABLE `network_can`
  ADD CONSTRAINT `network_can_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `network_hardware`
--
ALTER TABLE `network_hardware`
  ADD CONSTRAINT `network_hardware_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `network_lin`
--
ALTER TABLE `network_lin`
  ADD CONSTRAINT `network_lin_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `sender_id_fk` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pdbversion`
--
ALTER TABLE `pdbversion`
  ADD CONSTRAINT `pdbversion_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pdbversion_group`
--
ALTER TABLE `pdbversion_group`
  ADD CONSTRAINT `pdbversion_group_ibfk_1` FOREIGN KEY (`pdbversion_id`) REFERENCES `pdbversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pdbversion_group_ibfk_2` FOREIGN KEY (`vehicle_and_model_mapping_id`) REFERENCES `vehicle_and_model_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pdbversion_group_ibfk_3` FOREIGN KEY (`domain_and_features_mapping_id`) REFERENCES `domain_and_features_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `signals`
--
ALTER TABLE `signals`
  ADD CONSTRAINT `signals_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `status_notification`
--
ALTER TABLE `status_notification`
  ADD CONSTRAINT `notification_id_fk` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `receiver_id_fk` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `systemversion`
--
ALTER TABLE `systemversion`
  ADD CONSTRAINT `systemversion_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `systemversion_group`
--
ALTER TABLE `systemversion_group`
  ADD CONSTRAINT `systemversion_group_ibfk_1` FOREIGN KEY (`acbversion_id`) REFERENCES `acbversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `systemversion_group_ibfk_2` FOREIGN KEY (`systemversion_id`) REFERENCES `systemversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `systemversion_group_ibfk_3` FOREIGN KEY (`vehicleversion_id`) REFERENCES `vehicleversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `systemversion_group_ibfk_4` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `systemversion_group_ibfk_5` FOREIGN KEY (`domain_and_features_mapping_id`) REFERENCES `domain_and_features_mapping` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `systemversion_group_ibfk_6` FOREIGN KEY (`ecu_id`) REFERENCES `engine_control_unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `systemversion_group_ibfk_7` FOREIGN KEY (`variant_id`) REFERENCES `variants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `variants`
--
ALTER TABLE `variants`
  ADD CONSTRAINT `variants_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vehicleversion`
--
ALTER TABLE `vehicleversion`
  ADD CONSTRAINT `vehicleversion_ibfk_1` FOREIGN KEY (`created_or_updated_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vehicle_and_model_mapping`
--
ALTER TABLE `vehicle_and_model_mapping`
  ADD CONSTRAINT `vehicle_and_model_mapping_ibfk_1` FOREIGN KEY (`vehicleversion_id`) REFERENCES `vehicleversion` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vehicle_and_model_mapping_ibfk_2` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vehicle_and_model_mapping_ibfk_3` FOREIGN KEY (`model_id`) REFERENCES `vehiclemodel` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;