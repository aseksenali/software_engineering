import * as ActionTypes from './ActionTypes';

const Account = (state = {
    isLoading: true,
    isAuthenticated: false,
    username: null,
    password: null,
    authToken: null,
    errMess: null
}, action) => {
    switch (action.type) {
        case ActionTypes.AUTH_USER:
            return {...state, username: action.payload['username'], password: action.payload['password'], authToken: action.payload['token'], isLoading: false};
        case ActionTypes.REJECT_AUTH:
            return {...state, isLoading: false, errMess: action.payload};
        case ActionTypes.USER_LOADING:
            return {...state, isLoading: true, username: null, password: null, authToken: null, errMess: null};
        default:
            return state;
    }
};

export default Account;
