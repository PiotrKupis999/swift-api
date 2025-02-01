import React, { useState } from 'react';
import axios from 'axios';

const AddSwiftCode = () => {
    const [swiftCode, setSwiftCode] = useState('');
    const [bankName, setBankName] = useState('');
    const [countryISO2, setCountryISO2] = useState('');
    const [countryName, setCountryName] = useState('');
    const [address, setAddress] = useState('');
    const [isHeadquarter, setIsHeadquarter] = useState(false);
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/v1/swift-codes/", {
                swiftCode,
                bankName,
                countryISO2,
                countryName,
                address,
                isHeadquarter
            });
            setMessage(response.data.message);
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
    };

    return (
        <div>
            <h2>Add SWIFT code</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="SWIFT code" onChange={(e) => setSwiftCode(e.target.value)} value={swiftCode} /><br />
                <input type="text" placeholder="Bank Name" onChange={(e) => setBankName(e.target.value)} value={bankName} /><br />
                <input type="text" placeholder="Country ISO2 code" onChange={(e) => setCountryISO2(e.target.value)} value={countryISO2} /><br />
                <input type="text" placeholder="Country Name" onChange={(e) => setCountryName(e.target.value)} value={countryName} /><br />
                <input type="text" placeholder="Address" onChange={(e) => setAddress(e.target.value)} value={address} /><br />
                <label>
                    Is it a headquarter?
                    <input type="checkbox" onChange={(e) => setIsHeadquarter(e.target.checked)} checked={isHeadquarter} />
                </label>
                <br />
                <button type="submit">Add</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default AddSwiftCode;
