import React, { useEffect, useRef } from 'react';
import { Text, TouchableOpacity, StyleSheet, Dimensions, Animated } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { router } from 'expo-router';

const { width, height } = Dimensions.get('window');

export default function RoleSelection() {
  // -----------------------------
  // Animation values (same setup as Welcome.tsx)
  // -----------------------------
  const fadeAnim = useRef(new Animated.Value(0)).current;
  const blob1Anim = useRef(new Animated.Value(0)).current;
  const blob2Anim = useRef(new Animated.Value(0)).current;
  const blob3Anim = useRef(new Animated.Value(0)).current;
  const blobScale = useRef(new Animated.Value(0)).current;
  const blobRotate = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    // Fade-in animation for the content
    Animated.timing(fadeAnim, {
      toValue: 1,
      duration: 1200,
      useNativeDriver: true,
    }).start();

    // Helper function for looping animations
    const loopAnim = (anim: Animated.Value, duration: number) => {
      Animated.loop(
        Animated.sequence([
          Animated.timing(anim, { toValue: 1, duration, useNativeDriver: true }),
          Animated.timing(anim, { toValue: 0, duration, useNativeDriver: true }),
        ])
      ).start();
    };

    // Start blob animations (same durations as Welcome.tsx)
    loopAnim(blob1Anim, 5000);
    loopAnim(blob2Anim, 4000);
    loopAnim(blob3Anim, 6000);

    // Scale pulse animation
    Animated.loop(
      Animated.sequence([
        Animated.timing(blobScale, { toValue: 1, duration: 3000, useNativeDriver: true }),
        Animated.timing(blobScale, { toValue: 0, duration: 3000, useNativeDriver: true }),
      ])
    ).start();

    // Rotation loop for blob3
    Animated.loop(
      Animated.timing(blobRotate, {
        toValue: 1,
        duration: 12000,
        useNativeDriver: true,
      })
    ).start();
  }, []);

  // -----------------------------
  // Interpolations (map animation values to movement/rotation)
  // -----------------------------
  const blob1Y = blob1Anim.interpolate({ inputRange: [0, 1], outputRange: [-15, 15] });
  const blob2Y = blob2Anim.interpolate({ inputRange: [0, 1], outputRange: [-20, 20] });
  const blob3X = blob3Anim.interpolate({ inputRange: [0, 1], outputRange: [-15, 15] });
  const blob3Y = blob3Anim.interpolate({ inputRange: [0, 1], outputRange: [-10, 10] });
  const blobScaleVal = blobScale.interpolate({ inputRange: [0, 1], outputRange: [0.9, 1.1] });
  const blobRotateVal = blobRotate.interpolate({ inputRange: [0, 1], outputRange: ['0deg', '360deg'] });

  return (
    <LinearGradient
      colors={['#ffffff', '#f2f2f2', '#ff6b35']} // Same gradient background
      start={{ x: 0, y: 0 }}
      end={{ x: 1, y: 1 }}
      style={styles.container}
    >
      {/* Animated blobs (identical to Welcome.tsx) */}
      <Animated.View style={[styles.blob1, { transform: [{ translateY: blob1Y }, { scale: blobScaleVal }] }]} />
      <Animated.View style={[styles.blob2, { transform: [{ translateY: blob2Y }] }]} />
      <Animated.View style={[styles.blob3, { transform: [{ translateX: blob3X }, { translateY: blob3Y }, { rotate: blobRotateVal }] }]} />

      {/* Content (different from Welcome.tsx) */}
      <Animated.View style={{ opacity: fadeAnim, alignItems: 'center' }}>
        {/* Question at the top */}
        <Text style={styles.title}>Which one are you?</Text>

        {/* Parent button */}
        <TouchableOpacity style={styles.buttonPrimary} onPress={() => router.replace('/signup?role=parent')}>
          <Text style={styles.buttonText}>Parent</Text>
        </TouchableOpacity>

        {/* Driver button */}
        <TouchableOpacity style={styles.buttonSecondary} onPress={() => router.replace('/signup?role=driver')}>
          <Text style={styles.buttonTextSecondary}>Driver</Text>
        </TouchableOpacity>
      </Animated.View>
    </LinearGradient>
  );
}

// -----------------------------
// Stylesheet
// -----------------------------
const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: 'center', alignItems: 'center' },
  blob1: {
    position: 'absolute',
    width: 250,
    height: 250,
    borderRadius: 125,
    backgroundColor: '#ff6b35',
    top: -60,
    left: -80,
    opacity: 0.25,
  },
  blob2: {
    position: 'absolute',
    width: 180,
    height: 180,
    borderRadius: 90,
    backgroundColor: '#f2f2f2',
    bottom: -40,
    right: -60,
    opacity: 0.3,
  },
  blob3: {
    position: 'absolute',
    width: 120,
    height: 120,
    borderRadius: 60,
    backgroundColor: '#ff9b6b',
    top: height / 2.5,
    left: width / 2.5,
    opacity: 0.25,
  },
  title: {
    color: '#1a1a1a',
    fontSize: width * 0.08,
    fontWeight: '900',
    marginBottom: 15,
    letterSpacing: 1,
    textShadowColor: '#ff6b35',
    textShadowOffset: { width: 0, height: 2 },
    textShadowRadius: 4,
  },
  buttonPrimary: {
    backgroundColor: '#ff6b35',
    paddingVertical: 16,
    paddingHorizontal: 45,
    borderRadius: 35,
    marginBottom: 15,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.6,
    shadowRadius: 10,
    elevation: 8,
  },
  buttonSecondary: {
    backgroundColor: '#fff',
    paddingVertical: 16,
    paddingHorizontal: 45,
    borderRadius: 35,
    borderWidth: 2,
    borderColor: '#ff6b35',
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
    textTransform: 'uppercase',
    letterSpacing: 1.2,
  },
  buttonTextSecondary: {
    color: '#ff6b35',
    fontSize: 18,
    fontWeight: 'bold',
    textTransform: 'capitalize',
    letterSpacing: 1.2,
  },
});
