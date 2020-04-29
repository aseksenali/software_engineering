import {applyMiddleware, combineReducers, createStore} from 'redux';
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import account from './account';

export const ConfigureStore = () => {
    return createStore(
        account,
        applyMiddleware(thunk, logger)
    );
}
