import React, {Component} from 'react';
import {StyleSheet, Text, View, FlatList, AsyncStorage} from 'react-native';
import Main from "./components/Main";
import NovelDetail from "./components/NovelDetail";
import CreateNovel from "./components/CreateNovel";
import {StackNavigator} from 'react-navigation';
import Chart from "./components/Chart";

/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
// export default class App extends Component {
//     constructor(props) {
//         super(props);
//         this.state={};
//         global.novels = [];
//
//     }
//
//
// // AsyncStorage.setItem('@MySuperStore:key', JSON.stringify(global.novels));
//
//
//     render() {
//         return (<View><Text>Welcome</Text></View>);
//     }
// }
global.novels=[];
const ModalStack=StackNavigator({
    Home:{
        screen: Main
    },
    NovelDetail: {
        screen: NovelDetail,
    },
    Create: {
        screen: CreateNovel,
    },
    Chart: {
        screen: Chart,
    }
});
export default ModalStack;

