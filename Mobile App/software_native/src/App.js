import React from 'react';
import Dashboard from './components/dashboard/dashboard.jsx';
import {Provider} from 'react-redux';
import {ConfigureStore} from './redux/configureStore';

console.disableYellowBox = true;
const store = ConfigureStore();

const App = () => {
  return (
      <Provider store={store}>
        <Dashboard/>
      </Provider>
  );
};

export default App;
