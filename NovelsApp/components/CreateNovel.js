import {
    Button, Linking, TextInput, View, StyleSheet, Text, Alert, TouchableOpacity, AsyncStorage,
    Picker, Switch, Share,
} from 'react-native';
import React, {Component} from 'react';
// import Mailer from 'react-native-mail';
// import MailCompose from 'react-native-mail-compose';
import Communications from 'react-native-communications';
// import * as Link from "react-native";
// import SendIntentAndroid from 'react-native-send-intent';
// import {NumberPicker} from 'react-native-numberpicker'
export default class CreateNovel extends Component<{}> {

    constructor(props) {
        super(props);
        this.state = {
            selectedTargetAge: "14-18",
            options: {
                1: "5-8",
                2: "9-13",
                3: "14-18",
                4: "19-25",
                5: "26-30",
                6: "30+"
            },
            toggled: false
        };


        console.log("create");
    }

    handleEmail = () => {
        console.log("in mail");
        let to = ['rosanabalanescu@gmail.com'];
        let cc = [];
        let bcc = [];
        let subject = "Novel subscription - NovelApp";
        let body = "You just subscribed to novel: " + this.state.nTitle;
        Communications.email(to, cc, bcc, subject, body);
        // Mailer.mail({
        //     subject: 'need help',
        //     recipients: ['rosanabalanescu@gmail.com'],
        //     ccRecipients: ['supportCC@example.com'],
        //     bccRecipients: ['supportBCC@example.com'],
        //     body: '<b>A Bold Body</b>',
        //     isHTML: true,
        //     attachment: {
        //         path: '',  // The absolute path of the file from which to read data.
        //         type: '',   // Mime Type: jpg, png, doc, ppt, html, pdf
        //         name: '',   // Optional: Custom filename for attachment
        //     }
        // }, (error, event) => {
        //     Alert.alert(
        //         error.message,
        //         event,
        //         [
        //             {text: 'Ok', onPress: () => console.log('OK: Email Error Response')},
        //             {text: 'Cancel', onPress: () => console.log('CANCEL: Email Error Response')}
        //         ],
        //         { cancelable: true }
        //     )
        // });

        // Linking.openUrl('mailto:rosanabalanescu@gmail.com?subject=You_subscribed_to_a_novel&body=body')
        //     .catch(err => console.error('An error occurred', err));

        // Share.share({message: body});
    };

    // async sendMail() {
    //     try {
    //         await MailCompose.send({
    //             toRecipients: ['rosanabalanescu@gmail.com'],
    //             ccRecipients: [],
    //             bccRecipients: [],
    //             subject: 'This is subject',
    //             text: 'This is body',
    //             html: '<p>This is <b>html</b> body</p>', // Or, use this if you want html body. Note that some Android mail clients / devices don't support this properly.
    //             attachments: [{
    //                 filename: 'mytext', // [Optional] If not provided, UUID will be generated.
    //                 ext: '.txt',
    //                 mimeType: 'text/plain',
    //                 text: 'Hello my friend', // Use this if the data is in UTF8 text.
    //                 data: '...BASE64_ENCODED_STRING...', // Or, use this if the data is not in plain text.
    //             }],
    //         });
    //     } catch (e) {
    //         // e.code may be 'cannotSendMail' || 'cancelled' || 'saved' || 'failed'
    //     }
    // }


    add() {
        //TODO get novels from global?
        //TODO nu se adauga cu target age bun
        let title = this.state.nTitle;
        let content = this.state.nContent;
        console.log(this.state);
        // if (title !== undefined && content !== undefined)
        {
            AsyncStorage.getItem('novels').then((value) => {
                let novels = JSON.parse(value);
                let ids = novels.map(n => n.id);
                let maxId = ids.reduce(function (a, b) {
                    return Math.max(a, b);
                });
                maxId = maxId + 1;
                let newNovel = {
                    id: maxId,
                    title: this.state.nTitle,
                    author: "Me, myself and I",
                    content: this.state.nContent,
                    age: this.state.selectedTargetAge,
                };

                novels.push(newNovel);

                console.log(novels);

                AsyncStorage.setItem('novels', JSON.stringify(novels)).then(
                    () => {
                        // SendIntentAndroid.sendMail("your@address.com", "Subject test", "Test body");
                        console.log(this.state.toggled);
                        if (this.state.toggled === true)
                            this.handleEmail();
                        this.props.navigation.navigate("Home");
                    })
                    .catch((error) => {
                        console.log("error at adding");
                        alert(error.message);
                    });


            }).catch((error) => {
                console.log("error at something...");
                alert(error.message);
            });
        }
    }

    change(value) {
        console.log(value);
        this.setState({toggled: value});
    }

    render() {
        // let NumberPicker = require('react-native-numberpicker');
        return (
            <View style={styles.myView}>
                <Text style={styles.myText}>Start a new novel</Text>
                <View style={styles.inputForm}>

                    <TextInput style={styles.inputText}
                               placeholder='title'
                               multiline={true}
                               numberOfLines={2}
                               onChangeText={(text) => this.setState({nTitle: text})}/>


                    <TextInput style={styles.inputText1}
                               placeholder='There once was a ...'
                               multiline={true}
                               numberOfLines={4}
                               onChangeText={(text) => this.setState({nContent: text})}/>

                    <View style={{flexDirection: 'row', width: 300}}>
                        <Text style={styles.myLabel}>Target age: </Text>
                        <Picker itemStyle={{color: '#c9b0c7'}}
                                style={styles.picker}
                                mode="dropdown"
                                selectedValue={this.state.selectedTargetAge}
                                onValueChange={(itemValue, itemIndex) => this.setState({selectedTargetAge: itemValue})}>
                            {Object.keys(this.state.options).map((key) => {
                                return (<Picker.Item label={this.state.options[key]}
                                                     value={key}
                                                     key={key}
                                                     color='#c9b0c7'/>)
                            })}
                        </Picker>
                    </View>
                    <View style={{flexDirection: 'row', width: 300}}>
                        <Text>Subscribe to changes: </Text>
                        <Switch style={styles.switch}
                                onValueChange={(value) => this.change(value)}
                                value={this.state.toggled}/>


                    </View>

                </View>


                {/*<View style={styles.myButton}>*/}
                <TouchableOpacity onPress={() => {
                    this.add()
                }}>
                    <View style={styles.myButton}>
                        <Text style={styles.btnText}>Add</Text>
                    </View>
                </TouchableOpacity>
                {/*</View>*/}
            </View>
        );
    }
};
const styles = StyleSheet.create({
    myView: {
        height: 600,
        width: 410,
        backgroundColor: '#FDEBD0'
    },
    btnText: {
        textAlign: 'center',
        color: 'white',
        fontSize: 20,
    },
    myText: {
        marginTop: 20,
        marginLeft: 60,
        fontSize: 30,
        color: '#c987ba'
    },
    inputText: {
        marginTop: 10,
        height: 40,
        width: 300,
        backgroundColor: '#c9b0c7',
        color: 'white',
    },
    inputForm: {
        marginTop: 20,
        marginLeft: 50
    },
    myButton: {
        height: 50,
        width: 100,
        marginTop: 30,
        marginLeft: 150,
        backgroundColor: '#c9b0c7',
        borderRadius: 20,
        padding: 10,
        // shadowOffset: {
        //     width:0,
        //     height:3
        // },
        // shadowRadius:10,
        // shadowOpacity:0.25
    },
    inputText1: {
        marginTop: 10,
        width: 300,
        backgroundColor: '#c987ba',
        color: 'white',
    },

    listItem: {
        marginTop: 10,
        fontSize: 20,
        color: '#c987ba'
    },
    picker: {
        flex: 1
    },
    myLabel: {
        flex: 1,
        marginTop: 10,
    },

    switch: {
        marginLeft: 10,
    },
});
