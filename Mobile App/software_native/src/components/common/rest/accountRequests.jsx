import React from 'react';
import globals from '../../common/globals.jsx';
import {AsyncStorage} from 'react-native';

export default class AccountRequests extends React.Component {
    signUp = (username, password) => {
        console.log('Account signUp: ' + username + ' ' + password);
        return fetch('http://192.168.118.194:8766/auth/', {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                password: password,
            }),
        })
            .then((response) => response.json())
            .then((responseJson) => {
                console.log('Access token' + responseJson.headers['Authorization']);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    getAccessToken = (username, password) => {
        console.log('Account getAccessToken: ' + username + ' ' + password);
        const details = {
            'username': username,
            'password': password,
        };

        return fetch('http://' + globals.SERVER_AUTH_URL_ADDRESS + '/auth', {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${globals.ACCESS_TOKEN_KEY}`,
                Accept: '*/*',
                'Content-Type': 'application/json',
            },
            body: details,
        })
            .then((response) => response.json())
            .then((responseJson) => {
                console.log('account getAccessToken: ' + responseJson);
                this.storeData(responseJson.header['Authorization'].split(' ')[1], username);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    storeData = async (userToken, username) => {
        try {
            let multiDataSet = [
                [globals.ACCESS_TOKEN_KEY, userToken],
                [globals.USERNAME_TOKEN_KEY, username],
            ];
            await AsyncStorage.multiSet(multiDataSet);
        } catch (error) {
            console.log('couldn\'t save user access token to storage because of: ' + error);
        }
    };
}
