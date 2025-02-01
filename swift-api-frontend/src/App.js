import React from 'react';
import AddSwiftCode from './components/AddSwiftCode';
import DeleteSwiftCode from './components/DeleteSwiftCode';
import GetSwiftCode from './components/GetSwiftCode';
import GetBanksByCountry from './components/GetBanksByCountry';

function App() {
  return (
    <div className="App">
      <h1>SWIFT Code API</h1>
      <div>
        <h2>Options:</h2>
        <AddSwiftCode />
        <DeleteSwiftCode />
        <GetSwiftCode />
        <GetBanksByCountry />
      </div>
    </div>
  );
}

export default App;
