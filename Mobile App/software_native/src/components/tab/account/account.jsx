import React from 'react';
import {View, Image, Text} from 'react-native';
import {FormGroup, Input, Label, Button, Fieldset} from 'react-native-clean-form';
import AccountRequests from '../../common/rest/accountRequests.jsx';
import globals from '../../common/globals.jsx';
import {connect} from 'react-redux';
import {baseUrl} from '../../../shared/baseUrl';
import styles from './styles.jsx';
import * as ActionTypes from '../../../redux/ActionTypes';
import styled from 'styled-components/native';

const mapStateToProps = state => {
    return {
        username: state.username,
        password: state.password,
    };
};

const mapDispatchToProps = dispatch => {
    return ({
        authUser: (username, password) => dispatch({
            type: ActionTypes.AUTH_USER,
            username: username,
            password: password,
        }),
    });
};

class Account extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: 'admin',
            password: 'F235vk26',
        };
    }

    render() {
        const MyInput = styled.Input`color: red;`;
        return (
            <View style={styles.container}>
                <View style={styles.editPhotoPackage}>
                    <Image style={styles.image} source={require('../../../resources/account.png')}/>
                    <Text style={styles.contentTextHeader}>{this.state.username}</Text>
                    <Text style={styles.textHeaderDescription}>{this.state.status}</Text>
                </View>

                <View style={styles.inputPackage}>
                    <Fieldset label="Login">
                        <FormGroup>
                            <Label for="username">Username</Label>
                            <MyInput placeholder="user" id="username" name="username" value={this.props.username}/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="password">Password</Label>
                            <MyInput placeholder="password" id="password" name="password" value={this.props.password}/>
                        </FormGroup>
                    </Fieldset>
                </View>
                <Button onPress={() => this.props.authUser(this.state.username, this.state.password)}>Sign Up</Button>
            </View>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Account);
