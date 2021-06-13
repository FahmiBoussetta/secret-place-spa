import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEstablishment } from 'app/shared/model/establishment.model';
import { getEntities as getEstablishments } from 'app/entities/establishment/establishment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './housing-template.reducer';
import { IHousingTemplate } from 'app/shared/model/housing-template.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHousingTemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HousingTemplateUpdate = (props: IHousingTemplateUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { housingTemplateEntity, establishments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/housing-template');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEstablishments();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...housingTemplateEntity,
        ...values,
        establishmentId: establishments.find(it => it.id.toString() === values.establishmentIdId.toString()),
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
          <h2 id="secretplacespaApp.housingTemplate.home.createOrEditLabel" data-cy="HousingTemplateCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.housingTemplate.home.createOrEditLabel">Create or edit a HousingTemplate</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : housingTemplateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="housing-template-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="housing-template-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="housingTypeLabel" for="housing-template-housingType">
                  <Translate contentKey="secretplacespaApp.housingTemplate.housingType">Housing Type</Translate>
                </Label>
                <AvInput
                  id="housing-template-housingType"
                  data-cy="housingType"
                  type="select"
                  className="form-control"
                  name="housingType"
                  value={(!isNew && housingTemplateEntity.housingType) || 'SPA_PREMIUM'}
                >
                  <option value="SPA_PREMIUM">{translate('secretplacespaApp.HousingType.SPA_PREMIUM')}</option>
                  <option value="SPA">{translate('secretplacespaApp.HousingType.SPA')}</option>
                </AvInput>
                <UncontrolledTooltip target="housingTypeLabel">
                  <Translate contentKey="secretplacespaApp.housingTemplate.help.housingType" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="nbOfUnitLabel" for="housing-template-nbOfUnit">
                  <Translate contentKey="secretplacespaApp.housingTemplate.nbOfUnit">Nb Of Unit</Translate>
                </Label>
                <AvField
                  id="housing-template-nbOfUnit"
                  data-cy="nbOfUnit"
                  type="string"
                  className="form-control"
                  name="nbOfUnit"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 1, errorMessage: translate('entity.validation.min', { min: 1 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
                <UncontrolledTooltip target="nbOfUnitLabel">
                  <Translate contentKey="secretplacespaApp.housingTemplate.help.nbOfUnit" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="nbMaxOfOccupantsLabel" for="housing-template-nbMaxOfOccupants">
                  <Translate contentKey="secretplacespaApp.housingTemplate.nbMaxOfOccupants">Nb Max Of Occupants</Translate>
                </Label>
                <AvField
                  id="housing-template-nbMaxOfOccupants"
                  data-cy="nbMaxOfOccupants"
                  type="string"
                  className="form-control"
                  name="nbMaxOfOccupants"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 1, errorMessage: translate('entity.validation.min', { min: 1 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
                <UncontrolledTooltip target="nbMaxOfOccupantsLabel">
                  <Translate contentKey="secretplacespaApp.housingTemplate.help.nbMaxOfOccupants" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="housing-template-price">
                  <Translate contentKey="secretplacespaApp.housingTemplate.price">Price</Translate>
                </Label>
                <AvField
                  id="housing-template-price"
                  data-cy="price"
                  type="string"
                  className="form-control"
                  name="price"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
                <UncontrolledTooltip target="priceLabel">
                  <Translate contentKey="secretplacespaApp.housingTemplate.help.price" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="housing-template-establishmentId">
                  <Translate contentKey="secretplacespaApp.housingTemplate.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="housing-template-establishmentId"
                  data-cy="establishmentId"
                  type="select"
                  className="form-control"
                  name="establishmentIdId"
                >
                  <option value="" key="0" />
                  {establishments
                    ? establishments.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/housing-template" replace color="info">
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
  establishments: storeState.establishment.entities,
  housingTemplateEntity: storeState.housingTemplate.entity,
  loading: storeState.housingTemplate.loading,
  updating: storeState.housingTemplate.updating,
  updateSuccess: storeState.housingTemplate.updateSuccess,
});

const mapDispatchToProps = {
  getEstablishments,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HousingTemplateUpdate);
