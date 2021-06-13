import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './favorites.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFavoritesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FavoritesDetail = (props: IFavoritesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { favoritesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="favoritesDetailsHeading">
          <Translate contentKey="secretplacespaApp.favorites.detail.title">Favorites</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{favoritesEntity.id}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.favorites.userId">User Id</Translate>
          </dt>
          <dd>{favoritesEntity.userId ? favoritesEntity.userId.id : ''}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.favorites.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{favoritesEntity.establishmentId ? favoritesEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/favorites" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/favorites/${favoritesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ favorites }: IRootState) => ({
  favoritesEntity: favorites.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FavoritesDetail);
