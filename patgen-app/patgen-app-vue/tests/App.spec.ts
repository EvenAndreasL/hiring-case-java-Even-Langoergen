import { expect } from 'chai'
import { test } from 'vitest'
import { mount } from '@vue/test-utils'
import App from '../src/App.vue'

// initial test to see that testing config is working
test('renders heading correctly', () => {
    const wrapper = mount(App)
    const heading = wrapper.get('h1') // replace 'h1' with the correct selector for your heading
    expect(heading.text()).to.equal('Welcome to the frontend!')
});