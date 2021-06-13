import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHousingTemplate } from 'app/shared/model/housing-template.model';
import { getEntities as getHousingTemplates } from 'app/entities/housing-template/housing-template.reducer';
import { getEntity, updateEntity, createEntity, reset } from './housing-closure.reducer';
import { IHousingClosure } from 'app/shared/model/housing-closure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHousingClosureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HousingClosureUpdate = (props: IHousingClosureUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { housingClosureEntity, housingTemplates, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/housing-closure');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getHousingTemplates();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    if (errors.length === 0) {
      const entity = {
        ...housingClosureEntity,
        ...values,
        establishmentId: housingTemplates.find(it => it.id.toString() === values.establishmentIdId.toString()),
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
          <h2 id="secretplacespaApp.housingClosure.home.createOrEditLabel" data-cy="HousingClosureCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.housingClosure.home.createOrEditLabel">Create or edit a HousingClosure</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : housingClosureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="housing-closure-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="housing-closure-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="housing-closure-startDate">
                  <Translate contentKey="secretplacespaApp.housingClosure.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="housing-closure-startDate"
                  data-cy="startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.housingClosureEntity.startDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="housing-closure-endDate">
                  <Translate contentKey="secretplacespaApp.housingClosure.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="housing-closure-endDate"
                  data-cy="endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.housingClosureEntity.endDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="causeLabel" for="housing-closure-cause">
                  <Translate contentKey="secretplacespaApp.housingClosure.cause">Cause</Translate>
                </Label>
                <AvField
                  id="housing-closure-cause"
                  data-cy="cause"
                  type="text"
                  name="cause"
                  validate={{
                    maxLength: { value: 250, errorMessage: translate('entity.validation.maxlength', { max: 250 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="housing-closure-establishmentId">
                  <Translate contentKey="secretplacespaApp.housingClosure.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="housing-closure-establishmentId"
                  data-cy="establishmentId"
                  type="select"
                  className="form-control"
                  name="establishmentIdId"
                >
                  <option value="" key="0" />
                  {housingTemplates
                    ? housingTemplates.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/housing-closure" replace color="info">
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
  housingTemplates: storeState.housingTemplate.entities,
  housingClosureEntity: storeState.housingClosure.entity,
  loading: storeState.housingClosure.loading,
  updating: storeState.housingClosure.updating,
  updateSuccess: storeState.housingClosure.updateSuccess,
});

const mapDispatchToProps = {
  getHousingTemplates,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HousingClosureUpdate);
