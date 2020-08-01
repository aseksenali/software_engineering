import * as ActionTypes from './ActionTypes';
import {baseUrl} from '../shared/baseUrl';

const fetchAuthToken = (username, password) => (dispatch) => {
    dispatch(userLoading());
    return fetch(baseUrl + 'auth/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: {
            username: username,
            password: password
        }
    }).then(response => {
        if (response.ok) {
            return response;
        } else {
            const error = new Error('Error ' + response.status + ': ' + response.statusText);
            error.response = response;
            throw error;
        }
    },
        error => {
            throw new Error(error.message);
        })
        .then(response => response.json())
        .then(authToken => dispatch(addToken(username, password, authToken.headers['Authorization'])))
        .catch(error => dispatch(authFailed(error.message)));
};

export const authFailed = (errMess) => ({
    type: ActionTypes.REJECT_AUTH,
    payload: errMess
});

export const addToken = (username, password, token) => ({
    type: ActionTypes.AUTH_USER,
    payload: {
        username: username,
        password: password,
        token: token
    }
});

export const userLoading = () => ({
    type: ActionTypes.USER_LOADING
})
