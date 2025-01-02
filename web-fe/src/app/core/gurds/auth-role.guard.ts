import { AuthGuardData, createAuthGuard } from 'keycloak-angular';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { inject } from '@angular/core';
import Keycloak from 'keycloak-js';

const isAccessAllowed = async (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
  authData: AuthGuardData
): Promise<boolean | UrlTree> => {
  const { authenticated, grantedRoles } = authData;

  const requiredRole = route.data['role'];
  const router = inject(Router);
  const keycloak = inject(Keycloak);

  if (!authenticated) {
    keycloak.login({
      redirectUri: window.location.href,
    });
    return false;
  }

  // const hasRequiredRole = (role: string): boolean =>
  //   Object.values(grantedRoles.resourceRoles).some((roles) =>
  //     roles.includes(role)
  //   );

  // if (requiredRole && hasRequiredRole(requiredRole)) {
  //   return true;
  // }

  // Redirect to forbidden page if authenticated but lacks role
  // return router.parseUrl('/forbidden');
  return true;
};

export const canActivateAuthRole =
  createAuthGuard<CanActivateFn>(isAccessAllowed);
