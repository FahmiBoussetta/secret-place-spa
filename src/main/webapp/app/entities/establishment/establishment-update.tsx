import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './establishment.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEstablishmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EstablishmentUpdate = (props: IEstablishmentUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { establishmentEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/establishment');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...establishmentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="secretplacespaApp.establishment.home.createOrEditLabel" data-cy="EstablishmentCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.establishment.home.createOrEditLabel">Create or edit a Establishment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : establishmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="establishment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="establishment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="establishment-name">
                  <Translate contentKey="secretplacespaApp.establishment.name">Name</Translate>
                </Label>
                <AvField
                  id="establishment-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                    maxLength: { value: 150, errorMessage: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                />
                <UncontrolledTooltip target="nameLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.name" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="establishment-address">
                  <Translate contentKey="secretplacespaApp.establishment.address">Address</Translate>
                </Label>
                <AvField
                  id="establishment-address"
                  data-cy="address"
                  type="text"
                  name="address"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                    maxLength: { value: 150, errorMessage: translate('entity.validation.maxlength', { max: 150 }) },
                  }}
                />
                <UncontrolledTooltip target="addressLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.address" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="latitudeLabel" for="establishment-latitude">
                  <Translate contentKey="secretplacespaApp.establishment.latitude">Latitude</Translate>
                </Label>
                <AvField id="establishment-latitude" data-cy="latitude" type="string" className="form-control" name="latitude" />
                <UncontrolledTooltip target="latitudeLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.latitude" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="longitudeLabel" for="establishment-longitude">
                  <Translate contentKey="secretplacespaApp.establishment.longitude">Longitude</Translate>
                </Label>
                <AvField id="establishment-longitude" data-cy="longitude" type="string" className="form-control" name="longitude" />
                <UncontrolledTooltip target="longitudeLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.longitude" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="globalRateLabel" for="establishment-globalRate">
                  <Translate contentKey="secretplacespaApp.establishment.globalRate">Global Rate</Translate>
                </Label>
                <AvField id="establishment-globalRate" data-cy="globalRate" type="string" className="form-control" name="globalRate" />
                <UncontrolledTooltip target="globalRateLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.globalRate" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="establishmentTypeLabel" for="establishment-establishmentType">
                  <Translate contentKey="secretplacespaApp.establishment.establishmentType">Establishment Type</Translate>
                </Label>
                <AvInput
                  id="establishment-establishmentType"
                  data-cy="establishmentType"
                  type="select"
                  className="form-control"
                  name="establishmentType"
                  value={(!isNew && establishmentEntity.establishmentType) || 'SECRETPLACESPA'}
                >
                  <option value="SECRETPLACESPA">{translate('secretplacespaApp.EstablishmentType.SECRETPLACESPA')}</option>
                </AvInput>
                <UncontrolledTooltip target="establishmentTypeLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.establishmentType" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup check>
                <Label id="hasParkingLabel">
                  <AvInput
                    id="establishment-hasParking"
                    data-cy="hasParking"
                    type="checkbox"
                    className="form-check-input"
                    name="hasParking"
                  />
                  <Translate contentKey="secretplacespaApp.establishment.hasParking">Has Parking</Translate>
                </Label>
                <UncontrolledTooltip target="hasParkingLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.hasParking" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup check>
                <Label id="hasRestaurantLabel">
                  <AvInput
                    id="establishment-hasRestaurant"
                    data-cy="hasRestaurant"
                    type="checkbox"
                    className="form-check-input"
                    name="hasRestaurant"
                  />
                  <Translate contentKey="secretplacespaApp.establishment.hasRestaurant">Has Restaurant</Translate>
                </Label>
                <UncontrolledTooltip target="hasRestaurantLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.hasRestaurant" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup check>
                <Label id="hasFreeWifiLabel">
                  <AvInput
                    id="establishment-hasFreeWifi"
                    data-cy="hasFreeWifi"
                    type="checkbox"
                    className="form-check-input"
                    name="hasFreeWifi"
                  />
                  <Translate contentKey="secretplacespaApp.establishment.hasFreeWifi">Has Free Wifi</Translate>
                </Label>
                <UncontrolledTooltip target="hasFreeWifiLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.hasFreeWifi" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="establishment-description">
                  <Translate contentKey="secretplacespaApp.establishment.description">Description</Translate>
                </Label>
                <AvField
                  id="establishment-description"
                  data-cy="description"
                  type="text"
                  name="description"
                  validate={{
                    maxLength: { value: 300, errorMessage: translate('entity.validation.maxlength', { max: 300 }) },
                  }}
                />
                <UncontrolledTooltip target="descriptionLabel">
                  <Translate contentKey="secretplacespaApp.establishment.help.description" />
                </UncontrolledTooltip>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/establishment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  establishmentEntity: storeState.establishment.entity,
  loading: storeState.establishment.loading,
  updating: storeState.establishment.updating,
  updateSuccess: storeState.establishment.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentUpdate);
