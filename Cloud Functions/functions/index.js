/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

// const {onRequest} = require("firebase-functions/v2/https");
// const logger = require("firebase-functions/logger");

// const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

const {onDocumentCreated} = require("firebase-functions/v2/firestore");

exports.sendNotiOnNewDoc = onDocumentCreated("news/{docId}", async (event) => {
  const newValue = event.data.data();
  // Example: Get device token(s) (adjust the path as per your structure)
  const tokensSnapshot = await admin.firestore().collection("tokens").get();
  const tokens = tokensSnapshot.docs.map((doc) => doc.data().token);

  if (tokens.length === 0) {
    console.log("No device tokens found.");
    return null;
  }

  // Construct the notification
  const message = {
    notification: {
      title: "Új hír publikálva",
      body: `Egy új hír lett publikálva: ${newValue.title}`,
    },
    android: {
      notification: {
        channelId: "firebase_channel_1",
      },
    },
    tokens: tokens,
  };

  // Send the notification
  try {
    // const response = await admin.messaging().sendMulticast(message);
    const response = await admin.messaging().sendEachForMulticast(message);
    console.log(`${response.successCount} messages were sent successfully`);
    return null;
  } catch (error) {
    console.error("Error sending notification:", error);
  }
});

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
