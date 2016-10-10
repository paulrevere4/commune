/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import * as firebase from 'firebase';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

const firebaseConfig = {
    apiKey: "AIzaSyCVzI737kOTIiNVwSPvvh1cZ7-Rt0bq9XA",
    authDomain: "commune-bc88a.firebaseapp.com",
    databaseURL: "https://commune-bc88a.firebaseio.com",
    storageBucket: "commune-bc88a.appspot.com",
    messagingSenderId: "10565440446"
};

const firebaseApp = firebase.initializeApp(firebaseConfig);

class CommuneApp extends Component {
   render() {
      return (
         <View style={styles.container}>
            <Text style={styles.welcome}>
               Commune App!
            </Text>
         </View>
      );
   }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('Commune', () => CommuneApp);
