import { Tabs } from 'expo-router';
import React from 'react';

import { HapticTab } from '@/components/haptic-tab';
import { IconSymbol } from '@/components/ui/icon-symbol';
import { Colors } from '@/constants/theme';
import { useColorScheme } from '@/hooks/use-color-scheme';

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? 'light'].tint,
        headerShown: false,
        tabBarButton: HapticTab,
      }}
    >
      {/* Address tab */}
      <Tabs.Screen
        name="screens/generic/Address"
        options={{
          title: 'Address',
          tabBarIcon: ({ color }) => (
            <IconSymbol size={28} name="location.fill" color={color} />
          ),
        }}
      />

      {/* Contact tab */}
      <Tabs.Screen
        name="screens/generic/Contact"
        options={{
          title: 'Contact',
          tabBarIcon: ({ color }) => (
            <IconSymbol size={28} name="phone.fill" color={color} />
          ),
        }}
      />
    </Tabs>
  );
}