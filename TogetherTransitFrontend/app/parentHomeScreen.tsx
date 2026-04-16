import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { router } from 'expo-router';

export default function ParentHomeScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Parent Dashboard</Text>

      {/* Today's Ride Card */}
      <View style={styles.card}>
        <Text style={styles.cardTitle}>Today's Ride</Text>
        <Text>Driver: Sarah M.</Text>
        <Text>Pickup: 45 Main Street</Text>
        <Text>Time: 07:30 AM</Text>
      </View>

      {/* Live Tracking Placeholder */}
      <View style={styles.card}>
        <Text style={styles.cardTitle}>Live Tracking</Text>
        <Text>[Map Component Here]</Text>
      </View>

      {/* Navigate to Booking */}
      <Button title="Book a Ride" onPress={() => router.push('/welcome')} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 20, backgroundColor: '#fff' },
  header: { fontSize: 24, fontWeight: 'bold', marginBottom: 20 },
  card: { padding: 15, marginBottom: 15, backgroundColor: '#f9f9f9', borderRadius: 8 },
  cardTitle: { fontSize: 18, fontWeight: '600', marginBottom: 5 }
});
