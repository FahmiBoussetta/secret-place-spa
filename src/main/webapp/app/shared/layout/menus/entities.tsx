import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/booking">
      <Translate contentKey="global.menu.entities.booking" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/booking-element">
      <Translate contentKey="global.menu.entities.bookingElement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/favorites">
      <Translate contentKey="global.menu.entities.favorites" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rating">
      <Translate contentKey="global.menu.entities.rating" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/establishment">
      <Translate contentKey="global.menu.entities.establishment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/manage">
      <Translate contentKey="global.menu.entities.manage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/establishment-closure">
      <Translate contentKey="global.menu.entities.establishmentClosure" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/housing-template">
      <Translate contentKey="global.menu.entities.housingTemplate" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/housing-closure">
      <Translate contentKey="global.menu.entities.housingClosure" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/photo">
      <Translate contentKey="global.menu.entities.photo" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
