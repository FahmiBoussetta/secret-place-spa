import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './manage.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IManageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ManageDetail = (props: IManageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { manageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="manageDetailsHeading">
          <Translate contentKey="secretplacespaApp.manage.detail.title">Manage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{manageEntity.id}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.manage.userId">User Id</Translate>
          </dt>
          <dd>{manageEntity.userId ? manageEntity.userId.id : ''}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.manage.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{manageEntity.establishmentId ? manageEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/manage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/manage/${manageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ manage }: IRootState) => ({
  manageEntity: manage.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ManageDetail);
